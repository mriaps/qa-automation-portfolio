# QA Automation Portfolio

[![Run Selenium Tests](https://github.com/mriaps/qa-automation-portfolio/actions/workflows/tests.yml/badge.svg)](https://github.com/mriaps/qa-automation-portfolio/actions/workflows/tests.yml)

Portofolio pembelajaran dan praktik test automation menggunakan **Selenium WebDriver** dan **Java**, dibangun dengan prinsip **Page Object Model (POM)** untuk struktur kode yang rapi dan mudah dikembangkan.

Setiap perubahan kode diverifikasi otomatis lewat **GitHub Actions (CI)**, memastikan seluruh test tetap berjalan dengan baik setiap kali ada push atau pull request.

## 🛠️ Tech Stack

| Tools | Fungsi |
|---|---|
| Java 25 | Bahasa pemrograman utama |
| Selenium WebDriver 4.52.0 | Automation browser |
| TestNG 7.12.0 | Framework testing dan assertion |
| Maven | Dependency management dan build tool |
| Selenium Manager | Pengelolaan driver browser otomatis (bawaan Selenium, tanpa setup manual) |
| GitHub Actions | Continuous Integration — menjalankan test otomatis setiap push/PR |

## 📂 Struktur Project

```
src/test/java/com/mriaps/
├── pages/                  # Page Object: menyimpan locator dan aksi tiap halaman
│   ├── LoginPage.java
│   └── InventoryPage.java
└── tests/                  # Test case dan konfigurasi dasar
    ├── BaseTest.java       # Setup dan teardown browser (mendukung mode headless untuk CI)
    ├── LoginTest.java      # Test case untuk halaman login
    └── CartTest.java       # Test case untuk fitur keranjang belanja

.github/workflows/
└── tests.yml               # Workflow CI: menjalankan mvn test otomatis
```

Project mengikuti pola **Page Object Model**: logika interaksi dengan elemen halaman dipisah dari logika pengujian (assertion), sehingga kode lebih mudah dibaca, dipelihara, dan dikembangkan untuk halaman atau skenario baru.

## ✅ Test yang Tersedia

Target aplikasi: [SauceDemo](https://www.saucedemo.com)

| Test Case | Deskripsi |
|---|---|
| `shouldLoginSuccessfully` | Memverifikasi user berhasil login dan diarahkan ke halaman inventory |
| `shouldShowErrorForLockedOutUser` | Memverifikasi pesan error muncul saat user locked-out mencoba login |
| `shouldAddProductToCart` | Memverifikasi produk berhasil ditambahkan dan jumlah di keranjang bertambah |

## 🚀 Cara Menjalankan Test

**Prasyarat:**
- Java 25 (atau versi LTS terbaru)
- Maven 3.9+
- Google Chrome terpasang

**Jalankan seluruh test:**

```bash
mvn test
```

**Jalankan test tertentu (contoh: `CartTest`):**

```bash
mvn test -Dtest=CartTest
```

## ⚙️ Continuous Integration

Setiap push dan pull request ke branch `main` otomatis memicu GitHub Actions untuk:

1. Menyiapkan environment (Java 25 dan Google Chrome)
2. Menjalankan seluruh test dalam mode headless
3. Melaporkan hasilnya langsung di pull request

Lihat riwayat eksekusinya di tab [Actions](https://github.com/mriaps/qa-automation-portfolio/actions).

## 🗺️ Roadmap

- [x] Setup project Maven dengan Selenium dan TestNG
- [x] Implementasi Page Object Model
- [x] Test login (berhasil dan gagal)
- [x] Test tambah produk ke keranjang
- [x] Integrasi GitHub Actions untuk menjalankan test otomatis setiap push
- [ ] Test proses checkout
- [ ] Data-driven testing (parameterisasi test dengan beberapa set data)

## 👤 Tentang Saya

**Muhammad Rio Apriansah**

- 📧 Email: mrioapriansah22@gmail.com
- 💼 LinkedIn: [linkedin.com/in/mriaps](http://www.linkedin.com/in/mriaps)

Repo ini adalah bagian dari proses belajar saya membangun portofolio QA Automation. Terbuka untuk masukan, diskusi, atau kolaborasi.
