# QA Automation Portfolio

[![Run Selenium Tests](https://github.com/mriaps/qa-automation-portfolio/actions/workflows/tests.yml/badge.svg)](https://github.com/mriaps/qa-automation-portfolio/actions/workflows/tests.yml)

Portofolio pembelajaran dan praktik test automation menggunakan **Selenium WebDriver** dan **Java**, dibangun dengan prinsip **Page Object Model (POM)** untuk struktur kode yang rapi dan mudah dikembangkan.

Setiap perubahan kode diverifikasi otomatis lewat **GitHub Actions (CI)**, memastikan seluruh test tetap berjalan dengan baik setiap kali ada push atau pull request.

## 🛠️ Tech Stack

| Tools | Fungsi |
|---|---|
| Java 25 | Bahasa pemrograman utama |
| Selenium WebDriver 4.52.0 | Automation browser |
| TestNG 7.12.0 | Framework testing dan assertion, termasuk data-driven testing lewat `@DataProvider` |
| Maven | Dependency management dan build tool |
| Selenium Manager | Pengelolaan driver browser otomatis (bawaan Selenium, tanpa setup manual) |
| GitHub Actions | Continuous Integration — menjalankan test otomatis setiap push/PR |

## 📂 Struktur Project

```
src/test/java/com/mriaps/
├── config/                 # Pembacaan konfigurasi
│   └── ConfigReader.java   # Membaca config.properties (bisa di-override lewat -Dkey=value)
├── drivers/                # Pembuatan dan konfigurasi WebDriver
│   └── DriverFactory.java  # Membuat Chrome driver (opsi anti-popup, mode headless untuk CI)
├── listeners/              # TestNG listener
│   └── ScreenshotListener.java  # Mengambil screenshot otomatis saat test gagal
├── pages/                  # Page Object: menyimpan locator dan aksi tiap halaman
│   ├── LoginPage.java
│   ├── InventoryPage.java
│   ├── CartPage.java
│   └── CheckoutPage.java
├── tests/                  # Test case dan konfigurasi dasar
│   ├── BaseTest.java       # Siklus hidup test: setup dan teardown browser
│   ├── LoginTest.java      # Data-driven test: login berhasil dan ditolak
│   ├── CartTest.java       # Test case untuk fitur keranjang belanja
│   └── CheckoutTest.java   # Validasi cart, order summary, negative test, dan end-to-end checkout
└── utils/                  # Helper reusable
    ├── PriceUtils.java     # Mengubah teks harga (misal "$29.99") menjadi BigDecimal
    └── WaitHelper.java     # Explicit wait terpusat, timeout dibaca dari config

src/test/resources/
└── config.properties       # URL aplikasi, akun demo, dan timeout wait

.github/workflows/
└── tests.yml               # Workflow CI: menjalankan mvn test otomatis
```

Project mengikuti pola **Page Object Model**: logika interaksi dengan elemen halaman dipisah dari logika pengujian (assertion), sehingga kode lebih mudah dibaca, dipelihara, dan dikembangkan untuk halaman atau skenario baru.

## ✅ Test yang Tersedia

Target aplikasi: [SauceDemo](https://www.saucedemo.com)

| Test Case | Deskripsi |
|---|---|
| `shouldLoginSuccessfullyWithValidCredentials` | **Data-driven test**: memverifikasi login berhasil dan user diarahkan ke halaman inventory untuk `standard_user` dan `problem_user` |
| `shouldRejectInvalidLogin` | **Negative test data-driven** dengan 5 skenario: user terkunci, password salah, username tidak terdaftar, username kosong, dan password kosong. Memvalidasi pesan error dan memastikan user tetap di halaman login |
| `shouldAddProductToCart` | Memverifikasi produk berhasil ditambahkan dan jumlah di keranjang bertambah |
| `shouldShowCorrectProductDetailsInCart` | Memvalidasi isi keranjang: nama produk, quantity, dan harga sesuai dengan yang ditambahkan dari halaman inventory |
| `shouldCalculateOrderSummaryCorrectly` | Memvalidasi business logic ringkasan pesanan: subtotal, tax (8%), dan total pembayaran |
| `shouldCompleteCheckoutSuccessfully` | Test end-to-end: login → tambah produk → isi data checkout → validasi overview (produk dan subtotal) → selesaikan pesanan → verifikasi pesan sukses dan halaman `checkout-complete` |
| `shouldNotProceedWhenRequiredInformationIsMissing` | **Negative test data-driven**: memastikan checkout tidak bisa dilanjutkan jika first name, last name, atau zip code kosong, dengan memvalidasi pesan error dan posisi halaman |

## 🚀 Cara Menjalankan Test

**Prasyarat:**
- Java 25 (atau versi LTS terbaru)
- Maven 3.9+
- Google Chrome terpasang

**Jalankan seluruh test:**

```bash
mvn test
```

**Jalankan test tertentu (contoh: `CheckoutTest`):**

```bash
mvn test -Dtest=CheckoutTest
```

**Override konfigurasi lewat command line (contoh: environment lain):**

```bash
mvn test "-Dbase.url=https://staging.contoh.com"
```

Timeout explicit wait (default 10 detik) juga bisa diubah, misalnya saat CI sedang lambat:

```bash
mvn test "-Dtimeout.seconds=20"
```

Nilai default (URL aplikasi dan akun demo) ada di `src/test/resources/config.properties`. Parameter `-D` selalu diutamakan daripada isi file.

**Screenshot saat test gagal:** setiap test yang gagal otomatis diambil screenshot-nya dan disimpan di `target/screenshots/` (nama file memuat nama class, nama test, dan timestamp).

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
- [x] Test proses checkout end-to-end
- [x] Data-driven testing dengan TestNG `@DataProvider`

Semua target awal sudah tercapai. Ke depannya, repo ini akan terus dikembangkan seiring proses belajar, misalnya dengan menambah skenario test baru, reporting (Allure/ExtentReports), atau parallel test execution.

## 👤 Tentang Saya

**Muhammad Rio Apriansah**

- 📧 Email: mrioapriansah22@gmail.com
- 💼 LinkedIn: [linkedin.com/in/mriaps](http://www.linkedin.com/in/mriaps)

Repo ini adalah bagian dari proses belajar saya membangun portofolio QA Automation. Terbuka untuk masukan, diskusi, atau kolaborasi.
