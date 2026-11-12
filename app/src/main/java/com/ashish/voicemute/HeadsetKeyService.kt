package com.ashish.voicemute

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.SystemClock

class HeadsetKeyService : AccessibilityService() {
    private var lastHandledAt = 0L

    override fun onServiceConnected() {
        serviceInfo = serviceInfo.apply {
            flags = flags or AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS
            flags = flags or AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
        }
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode != KeyEvent.KEYCODE_HEADSETHOOK) return false
        if (event.action != KeyEvent.ACTION_UP) return true

        val now = SystemClock.elapsedRealtime()
        if (now - lastHandledAt < 650) return true
        lastHandledAt = now
        performMuteToggle()
        return true
    }

    private fun performMuteToggle() {
        val root = rootInActiveWindow ?: return
        val candidates = mutableListOf<AccessibilityNodeInfo>()
        collectNodes(root, candidates, 0)

        val keywords = listOf("mute", "unmute", "microphone", "mic")
        val target = candidates.firstOrNull { node ->
            val description = node.contentDescription?.toString()?.lowercase().orEmpty()
            val text = node.text?.toString()?.lowercase().orEmpty()
            val label = "$description $text"
            node.isEnabled && node.isVisibleToUser &&
                keywords.any { label.contains(it) } &&
                (node.isClickable || hasClickableParent(node))
        }

        if (target != null) {
            val clickable = findClickable(target) ?: target
            clickable.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    private fun collectNodes(
        node: AccessibilityNodeInfo,
        output: MutableList<AccessibilityNodeInfo>,
        depth: Int
    ) {
        if (depth > 18) return
        output.add(node)
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { child -> collectNodes(child, output, depth + 1) }
        }
    }

    private fun hasClickableParent(node: AccessibilityNodeInfo): Boolean {
        var parent = node.parent
        var depth = 0
        while (parent != null && depth++ < 8) {
            if (parent.isClickable) return true
            parent = parent.parent
        }
        return false
    }

    private fun findClickable(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        var current: AccessibilityNodeInfo? = node
        var depth = 0
        while (current != null && depth++ < 8) {
            if (current.isClickable) return current
            current = current.parent
        }
        return null
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) { }
    override fun onInterrupt() { }
}
