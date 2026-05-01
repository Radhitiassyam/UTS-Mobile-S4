# 📱 Seminar Registration App
### UTS Pemrograman Mobile I - TIF K 24 A
**Dosen:** Erryck Norrys, S.Kom | **Universitas Teknologi Bandung**

---

## 🎯 Deskripsi Aplikasi

Aplikasi Android untuk pendaftaran seminar mahasiswa. Dibangun menggunakan **Kotlin** + **Material Design** dengan alur:

> **Login / Register → Home → Form Pendaftaran → Konfirmasi → Hasil**

---

## 🗂️ Struktur Project

```
SeminarApp/
├── app/src/main/
│   ├── java/com/example/seminarapp/
│   │   ├── LoginActivity.kt          ← Halaman Login
│   │   ├── RegisterActivity.kt       ← Halaman Register
│   │   ├── HomeActivity.kt           ← Halaman Utama
│   │   ├── FormPendaftaranActivity.kt ← Form + Validasi
│   │   └── HasilActivity.kt          ← Halaman Hasil
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_login.xml
│   │   │   ├── activity_register.xml
│   │   │   ├── activity_home.xml
│   │   │   ├── activity_form_pendaftaran.xml
│   │   │   └── activity_hasil.xml
│   │   ├── values/
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   ├── themes.xml
│   │   │   └── dimens.xml
│   │   └── drawable/
│   │       ├── bg_gradient_header.xml
│   │       ├── bg_gradient_login.xml
│   │       ├── bg_gradient_success.xml
│   │       ├── bg_card_white.xml
│   │       ├── bg_circle_light.xml
│   │       ├── bg_spinner.xml
│   │       └── bg_info_item.xml
│   └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## ✅ Fitur yang Diimplementasikan

### 1. Halaman Login
- Form username & password dengan Material TextInputLayout
- Validasi: field wajib diisi, credential matching
- Akun hardcode: `admin/admin123`, `mahasiswa/password`, `utb/utb2025`, `user/123456`
- Tombol ke halaman Register

### 2. Halaman Register
- Form: Nama, Email, Password, Konfirmasi Password
- Real-time validation saat mengetik
- Kembali ke Login setelah sukses

### 3. Halaman Utama (Home)
- Menampilkan nama user yang login
- Preview 3 seminar pilihan dengan badge status
- Stats card (jumlah seminar, biaya, tahun)
- Tombol "Daftar Seminar" & "Keluar"

### 4. Form Pendaftaran Seminar
**Input Fields:**
- Nama Lengkap
- Email
- Nomor HP
- Jenis Kelamin (RadioButton)
- Pilihan Seminar (Spinner - 7 pilihan)
- Checkbox Persetujuan

**Validasi:**
- Semua field wajib diisi
- Email harus mengandung `@`
- Nomor HP: hanya angka, diawali `08`, panjang 10–13 digit
- ✅ **Real-time validation** (error langsung muncul saat mengetik)
- Checkbox harus dicentang sebelum submit

### 5. Dialog Konfirmasi
- Muncul setelah klik Submit (jika validasi lolos)
- Menampilkan ringkasan data yang diisi
- Tombol **Ya** → ke halaman Hasil
- Tombol **Tidak** → kembali ke form

### 6. Halaman Hasil
- Menampilkan semua data pendaftaran
- Pesan "Pendaftaran Berhasil 🎉"
- Tombol kembali ke Beranda

---

## 🎨 Desain UI/UX

- **Tema:** Material Design 3 (MaterialComponents.Light.NoActionBar)
- **Warna Utama:** Biru (#1565C0) untuk profesional & kepercayaan
- **Aksen:** Oranye (#FF6F00) untuk CTA
- **Warna Sukses:** Hijau (#2E7D32)
- **Gradient:** Header & Login menggunakan linear gradient
- **Card:** Rounded corners (16dp), elevation untuk depth
- **Typography:** Bold headers, clean body text
- **Spacing:** Padding konsisten 16–28dp

---

## 🔧 Cara Menjalankan

1. Buka Android Studio
2. File → Open → pilih folder `SeminarApp`
3. Tunggu Gradle sync selesai
4. Jalankan di emulator (API 24+) atau device fisik

**Requirements:**
- Android Studio Hedgehog / Iguana ke atas
- Kotlin 1.9+
- compileSdk 34, minSdk 24

---

## 🎬 Video Penjelasan

> 🔗 **[Link Video - Upload ke sini]**

Video mencakup:
- Penjelasan Halaman Login & Register
- Penjelasan Halaman Utama (Home)
- Demo Form Pendaftaran lengkap
- Demo validasi real-time (error saat mengetik)
- Demo Dialog Konfirmasi
- Penjelasan Halaman Hasil
- Penjelasan kode (Activity + XML)

---

## 👨‍💻 Informasi Mahasiswa

| Field | Detail |
|-------|--------|
| Nama | [Nama Mahasiswa] |
| NIM | [NIM] |
| Kelas | TIF K 24 A |
| Mata Kuliah | Pemrograman Mobile I |

---

*© 2025 Universitas Teknologi Bandung - Departemen Teknik Informatika*
