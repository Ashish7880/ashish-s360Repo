# Voice Mute Controller (Android prototype)

This is a minimal Android Studio project for testing whether a wired headset's single media button can trigger a visible Mute/Unmute control in the foreground app.

## Important limitations
- This is a prototype, not a guaranteed ChatGPT integration.
- Android/MIUI may route the headset button to the media system instead of this accessibility service.
- ChatGPT may not expose its mute control as an accessible node, or its label may change.
- This version does not use fixed screen coordinates and does not record audio.
- It only attempts a click when it finds a visible, enabled accessibility node labelled with terms such as "mute", "unmute", "microphone", or "mic".
- Accessibility access is powerful. Enable it only after reviewing the source, and disable it when you are finished testing.

## Build
1. Install Android Studio on a computer.
2. Open this folder as a project.
3. Allow Gradle sync to download the Android Gradle Plugin and Kotlin plugin.
4. Connect the POCO X5 Pro with USB debugging enabled, or build an APK from **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. Install the APK on the phone.
6. Open the app, choose **Open Accessibility Settings**, and enable **Voice Mute Controller** under installed/downloaded services.
7. Open ChatGPT Voice Mode and test one short press of the wired headset button.

If no response occurs, Android may not be forwarding the headset hook key to accessibility. Do not assume it is working without testing both the muted and unmuted states.
