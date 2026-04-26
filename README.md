# 💬 ChatApp

A real-time Android chat application built with **Kotlin**, **Firebase**, and **Clean Architecture**. Users can register, log in, browse topic-based chat rooms, and exchange messages instantly.

---

## ✨ Features

- 🔐 **Authentication** — Register and log in with email & password via Firebase Auth
- 🏠 **Chat Rooms** — Browse and join public rooms organized by category (Sports, Music, Movies, Gaming)
- ➕ **Create Rooms** — Create a new chat room with a name, description, and category
- 💬 **Real-Time Messaging** — Send and receive messages instantly using Firestore live listeners
- 📲 **Session Persistence** — Stay logged in across app restarts using SharedPreferences
- 🌙 **Dark Mode Support** — Fully themed for both light and dark modes
- 🔄 **Typing Indicator** — Animated dots shown while composing a message
- 🚀 **Splash Screen** — Auto-redirects to Home or Login based on session state

---

## 🏗️ Architecture

The project follows **Clean Architecture** with separation into three layers:

```
app/
├── data/
│   ├── repository/       # Repository implementations
│   └── session/          # SessionManager (SharedPreferences)
├── database/
│   ├── firestore/        # Firestore DAOs
│   └── model/            # Data models (UserData, MessageData, RommData, CategoryData)
├── domain/
│   └── usecase/          # Business logic use cases
│       ├── auth/         # Login, Register, Logout
│       ├── message/      # Send & Observe messages
│       └── room/         # Create & Get rooms
├── di/                   # Hilt dependency injection modules
└── ui/                   # Activities, ViewModels, Adapters
    ├── splash/
    ├── login/
    ├── register/
    ├── home/
    ├── room/
    └── chatpage/
```

### Design Patterns Used

- **MVVM** — ViewModels with LiveData for reactive UI
- **Repository Pattern** — Abstract data sources behind interfaces
- **Use Cases** — Single-responsibility business logic classes
- **Dependency Injection** — Hilt for all dependencies
- **Data Binding** — Two-way binding between XML layouts and ViewModels

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | XML Layouts, Data Binding, ViewBinding |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt (Dagger) |
| Backend | Firebase Firestore, Firebase Auth |
| Async | Kotlin Coroutines + Flow |
| Session | SharedPreferences + Gson |
| Min SDK | API 26 (Android 8.0) |
| Target SDK | API 34 (Android 14) |
| Compile SDK | API 35 |

---

## 📦 Dependencies

```kotlin
// Dependency Injection
implementation("com.google.dagger:hilt-android:2.50")

// Firebase
implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
implementation("com.google.firebase:firebase-auth")
implementation("com.google.firebase:firebase-firestore")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Gson
implementation("com.google.code.gson:gson:2.10.1")
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 11+
- A Firebase project




## 🗄️ Firestore Data Structure

```
firestore/
├── users/
│   └── {userId}/
│       ├── id: String
│       ├── userName: String
│       ├── firstName: String
│       └── lastName: String
│
└── rooms/
    └── {roomId}/
        ├── id: String
        ├── title: String
        ├── decription: String
        ├── categoryid: Int
        ├── adminId: String
        └── messages/
            └── {messageId}/
                ├── id: String
                ├── content: String
                ├── senderId: String
                ├── senderName: String
                ├── roomID: String
                └── timestamp: Timestamp
```

---

## 📋 Room Categories

| ID | Category |
|----|----------|
| 1  | Sports   |
| 2  | Music    |
| 3  | Movie    |
| 4  | Gaming   |

---



# 📁 Project Structure

```
ChatApp-master/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/chatapptest/
│   │   │   │   ├── ChatApplication.kt
│   │   │   │   ├── SessionProvider.kt
│   │   │   │   ├── data/
│   │   │   │   ├── database/
│   │   │   │   ├── di/
│   │   │   │   ├── domain/
│   │   │   │   ├── ui/
│   │   │   │   └── util/
│   │   │   └── res/
│   │   └── ...
│   ├── build.gradle.kts
│   └── google-services.json   ← (add this yourself)
├── gradle/
│   └── libs.versions.toml
└── settings.gradle.kts
```





