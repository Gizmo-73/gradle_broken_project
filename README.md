# gradle_broken_project
Build failed with gradle 6.5, project on encrypted exFAT file system disk

Project was located on encrypted with "VeraCrypt" exFAT file system disk. Build failed with an exception:
e: A:\test\build.gradle.kts: 9:1: Unresolved reference: loadLocalProjectProperties.
About system:
Encryption program: "VeraCrypt"
File system: exFAT
Java: Liberica Standard JDK 11.0.8.1 x86 64 bit for Windows
OS: Windows 10 Pro: 19042.572

Problem was resolved by relocate project on encrypted with "VeraCrypt" NTFS file system disk.
About system:
Encryption program: "VeraCrypt"
File system: NTFS
Java: Liberica Standard JDK 11.0.8.1 x86 64 bit for Windows
OS: Windows 10 Pro: 19042.572
