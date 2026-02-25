

A Kotlin + Jetpack Compose Android app for reading **MIFARE Classic** NFC cards, showing cardholder data on-screen, and then sending an arrival event to a backend service.

+## What this app does
+
+- Detects NFC tags in foreground mode using `NfcAdapter` in `MainActivity`.
+- Reads card blocks from a MIFARE Classic tag via `ReadDataSource`.
+- Builds a `CardData` domain model from the scanned fields.
+- Updates UI state through a `NfcViewModel` (`Idle` → `Loading` → `Success`/`Error`).
+- Triggers a backend “arrival” call after a successful scan.
+- Accepts keyboard-like external reader input (buffered until Enter key) as a fallback input path.
+
+## NFC card mapping used by the repository
+
+`NfcRepositoryImpl` currently reads these fields from sectors/blocks:

+## Tech stack
+
+- **Language**: Kotlin
+- **UI**: Jetpack Compose + Material 3
+- **DI**: Hilt
+- **Networking**: Retrofit + OkHttp
+- **Architecture style**: layered (`data` / `domain` / `presentation`) with ViewModel state flow
+
+## Requirements
+
+- Android Studio (recent stable)
+- Android SDK with:
+  - `compileSdk = 36`
+  - `minSdk = 27`
+  - `targetSdk = 36`
+- Physical Android device with NFC support (MIFARE Classic capable)
+
+## Setup
+
+1. Clone the repository.
+2. Open the project in Android Studio.
+3. Let Gradle sync dependencies.
+4. Connect a physical NFC-capable Android phone.
+5. Enable NFC on the device.
+6. Build and run the `app` module.
+
+### Command-line build
+
+```bash
+./gradlew assembleDebug
+```
+
+## Runtime flow
+
+1. App launches and shows splash screen.
+2. Main screen waits for an NFC tag.
+3. On tag discovery, app reads card data.
+4. UI shows cardholder information.
+5. App sends arrival status to backend and updates status card.
+
+## Permissions and hardware declarations
+
+The manifest currently declares:
+
+- `android.permission.NFC`
+- `android.permission.INTERNET`
+- `android.hardware.nfc` (required)
+- `android.hardware.usb.host` (optional)
+
+It also includes NFC tech filtering for `android.nfc.tech.MifareClassic`.
+
+## Project structure (high-level)
+
+```text
+app/src/main/java/com/example/nfcapp/
+├── MainActivity.kt
+├── NfcApp.kt
+├── core/
+│   ├── data/
+│   │   ├── repository/NfcRepositoryImpl.kt
+│   │   └── source/ReadDataSource.kt
+│   ├── domain/
+│   │   ├── model/Cardata.kt
+│   │   ├── repository/NfcReadRepository.kt
+│   │   └── usecase/
+│   │       ├── ReadClassicCardUseCase.kt
+│   │       └── SendArrivalUseCase.kt
+│   └── presentation/
+│       ├── NfcReadViewModel.kt
+│       └── ui/
+│           ├── nfcScreen.kt
+│           ├── infoCard.kt
+│           └── StatusCard.kt
+└── util/mergeNames.kt
+```
+
## Current development notes

+This repo appears to be in active development. Before production use, verify and complete missing pieces such as:
+
+- all referenced resources (`network_security_config`, `device_filter`, custom launcher mipmaps),
+- all presentation/domain classes referenced by imports,
+- and full DI wiring for repositories/services.
.
 
EOF
)
