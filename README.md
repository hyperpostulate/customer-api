# Customer API

A SQLite-based REST API that performs CRUD (Create, Read, Update, Delete) operations on customer records.

## Table of Contents

- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Data Model](#data-model)
- [Setup](#setup)
- [Running](#running)
- [Test](#test)
- [Docker](#docker)
- [CI/CD](#cicd)
- [License](#license)

## Tech Stack

| Category | Technology | Version |
|---|---|---|
| Language | Java | 25 (`--enable-preview`) |
| Framework | Spring Boot | 3.5.11 |
| Build Tool | Maven | With Wrapper |
| Database | SQLite | sqlite-jdbc 3.46.0.0 |
| ORM | Hibernate (Spring Data JPA) | 6.6.18.Final |
| Security | Spring Security | — |
| HATEOAS | Spring HATEOAS | 2.5.1 |
| Validation | Hibernate Validator | — |
| Monitoring | Spring Boot Actuator | — |
| API Docs | Spring REST Docs + Asciidoctor | — |
| Test | JUnit 5, Mockito, Podam, Gson | — |
| Container | Docker (eclipse-temurin:25-jre-alpine) | — |

## Architecture

The project follows a layered architecture:

```
src/main/java/org/mesutormanli/customerapi/
├── config/
│   └── PersistenceConfig.java        # JPA / SQLite configuration
├── controller/
│   ├── CustomerController.java       # Customer CRUD REST endpoints
│   └── IndexController.java          # Root endpoint (HATEOAS link)
├── model/
│   ├── converter/
│   │   └── CustomerConverter.java    # Entity ↔ DTO / Request conversions
│   ├── dto/
│   │   └── CustomerDto.java          # Data transfer object (record)
│   ├── entity/
│   │   └── CustomerEntity.java       # JPA entity (with soft-delete)
│   ├── hateoas/
│   │   └── CustomerControllerRepresentationModel.java
│   ├── request/
│   │   └── CustomerRequest.java      # Request body (record)
│   └── response/
│       ├── CustomerDeleteResponse.java
│       └── CustomerListResponse.java
├── repository/
│   └── CustomerRepository.java       # JPA Repository interface
└── service/
    ├── CustomerService.java          # Service interface
    └── impl/
        └── CustomerServiceImpl.java  # Service implementation
```

### Layer Responsibilities

- **Controller** — Handles HTTP requests and returns responses.
- **Service** — Executes business logic, orchestrates repository and converter.
- **Repository** — Provides automatic CRUD operations via Spring Data JPA.
- **Entity** — JPA entity mapped to the `CUSTOMER` table.
- **Converter** — Performs conversions between Entity, DTO, and Request.
- **Config** — Configures SQLite DataSource and Hibernate settings.

## API Endpoints

All endpoints run under the `/customer-api` context path.

| Method | Endpoint | Description | Request | Response | Status Codes |
|---|---|---|---|---|---|
| `GET` | `/` | Root directory, returns HATEOAS link | — | HATEOAS model | `200 OK` |
| `GET` | `/customer/{id}` | Retrieves customer by ID | — | `CustomerListResponse` | `200 OK`, `404 Not Found` |
| `GET` | `/customers` | Lists all customers | — | `CustomerListResponse` | `200 OK` |
| `POST` | `/customer` | Creates a new customer | `CustomerRequest` | `CustomerDto` | `201 Created` |
| `PUT` | `/customer/{id}` | Updates a customer | `CustomerRequest` | `CustomerDto` | `200 OK`, `404 Not Found` |
| `DELETE` | `/customer/{id}` | Soft-deletes a customer | — | `CustomerDeleteResponse` | `200 OK`, `204 No Content` |
| `DELETE` | `/customers` | Soft-deletes all customers | — | `CustomerDeleteResponse` | `200 OK`, `204 No Content` |

### Request / Response Examples

**Create Customer (POST /customer-api/customer)**

```json
{
  "name": "Ahmet",
  "surname": "Yılmaz",
  "age": 30,
  "address": "İstanbul, Türkiye",
  "telephone": "+90 555 123 45 67",
  "email": "ahmet@ornek.com",
  "nationality": "Türk",
  "maritalStatus": "Bekar"
}
```

**Successful Response (201 Created)**

```json
{
  "id": 1,
  "name": "Ahmet",
  "surname": "Yılmaz",
  "age": 30,
  "address": "İstanbul, Türkiye",
  "telephone": "+90 555 123 45 67",
  "email": "ahmet@ornek.com",
  "nationality": "Türk",
  "maritalStatus": "Bekar"
}
```

### Actuator Endpoints

| Endpoint | Description |
|---|---|
| `/actuator/health` | Application health check |
| `/actuator/info` | Application information |
| `/actuator/logfile` | Log file |
| `/actuator/metrics` | Metrics |

## Data Model

### CUSTOMER Table

| Column | Type | Description |
|---|---|---|
| `id` | `BIGINT` | Primary key (auto-generated) |
| `name` | `VARCHAR` | Customer name |
| `surname` | `VARCHAR` | Customer surname |
| `age` | `INTEGER` | Age |
| `address` | `VARCHAR` | Address |
| `telephone` | `VARCHAR` | Phone number |
| `email` | `VARCHAR` | Email address |
| `nationality` | `VARCHAR` | Nationality |
| `maritalStatus` | `VARCHAR` | Marital status |
| `deleted` | `BOOLEAN` | Soft-delete flag (default: false) |

### Soft Delete

Delete operations are performed as logical deletes (soft-delete) instead of physical deletes:
- `DELETE` queries are transformed into `UPDATE CUSTOMER SET deleted = true WHERE id=?`.
- Queries filter out deleted records by default (`@SQLRestriction`).
- This prevents data loss and allows data recovery.

## Setup

### Requirements

- **Java 25** (JDK with preview features enabled)
- **Maven 3.8+** (or Maven Wrapper)
- **Docker** (optional, for container deployment)

### Environment Variables

The project does not require any environment variables. All configuration is done in `.properties` files.

### Configuration

**`src/main/resources/application.properties`** — Server, error handling, logging, and Actuator settings.

**`src/main/resources/persistence.properties`** — SQLite connection, Hibernate dialect, and schema management.

The SQLite database file (`CustomerDB.sqlite`) is automatically created in the project root directory.

## Running

### With Maven

```bash
# Compile
mvn clean compile

# Package
mvn package

# Run
java --enable-preview -jar target/customer-api-0.0.1-SNAPSHOT.jar
```

### With Maven Wrapper

```bash
./mvnw clean package
java -jar target/customer-api-0.0.1-SNAPSHOT.jar
```

### With VS Code

You can run it using the "Customer API" configuration defined in the `.vscode/launch.json` file.

### With IntelliJ IDEA

Import the project as a Maven project and run the `CustomerApiApplication.main()` method.

## Test

```bash
mvn test
```

Tests use the following libraries:
- **JUnit 5** — Test framework
- **Mockito** — Mock objects
- **Podam** — Random test data generation
- **Gson** — JSON serialization
- **Spring REST Docs MockMvc** — API documentation snippets

Test classes:
- `BaseControllerTest` — Base class for controller tests
- `BaseServiceTest` — Base class for service tests
- `CustomerControllerTest` — Customer controller tests (with REST Docs)
- `CustomerServiceTest` — Service layer unit tests
- `CustomerEntityTest`, `CustomerRequestTest`, `CustomerConverterTest`, etc. — Model tests

## Docker

```bash
# Build image
docker build -t customer-api .

# Run container
docker run -p 8080:8080 customer-api
```

The Docker image is based on `eclipse-temurin:25-jre-alpine` and runs the JAR file.

## CI/CD

Continuous integration is provided via GitHub Actions:

| Workflow | Trigger | Description |
|---|---|---|
| **Java CI with Maven** (`maven.yml`) | Push/PR to `master` branch | Maven build with JDK 25 |
| **CodeQL** (`codeql.yml`) | Push/PR to `master` branch, weekly | Security analysis |
| **Qodana** (`qodana_code_quality.yml`) | Push/PR to `master` branch | Code quality analysis |

## Project Structure

```
customer-api/
├── .github/workflows/     # GitHub Actions CI/CD
├── .mvn/wrapper/          # Maven Wrapper
├── .vscode/               # VS Code configurations
├── src/
│   ├── main/
│   │   ├── java/.../      # Source code
│   │   └── resources/     # Configuration files
│   └── test/java/.../     # Test code
├── logs/                  # Application logs
├── Dockerfile             # Docker image definition
├── pom.xml                # Maven project definition
├── qodana.yaml            # Qodana code quality settings
├── LICENSE.txt            # GPL-3.0 license
└── CustomerDB.sqlite      # SQLite database (created at runtime)
```

## Contributing

1. Fork this repository
2. Create a new branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push your branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**. See the `LICENSE.txt` file for details.

---

**Developer:** Mesut Ormanlı

---

# Customer API

Müşteri kayıtları üzerinde CRUD (Create, Read, Update, Delete) işlemleri gerçekleştiren, SQLite tabanlı bir REST API.

## İçindekiler

- [Teknoloji Yığını](#teknoloji-yığını)
- [Mimari](#mimari)
- [API Uç Noktaları](#api-uç-noktaları)
- [Veri Modeli](#veri-modeli)
- [Kurulum](#kurulum)
- [Çalıştırma](#çalıştırma)
- [Test](#test)
- [Docker](#docker)
- [CI/CD](#cicd)
- [Lisans](#lisans)

## Teknoloji Yığını

| Kategori | Teknoloji | Sürüm |
|---|---|---|
| Dil | Java | 25 (`--enable-preview`) |
| Framework | Spring Boot | 3.5.11 |
| Yapı Aracı | Maven | Wrapper ile |
| Veritabanı | SQLite | sqlite-jdbc 3.46.0.0 |
| ORM | Hibernate (Spring Data JPA) | 6.6.18.Final |
| Güvenlik | Spring Security | — |
| HATEOAS | Spring HATEOAS | 2.5.1 |
| Doğrulama | Hibernate Validator | — |
| Monitor | Spring Boot Actuator | — |
| API Dokümanı | Spring REST Docs + Asciidoctor | — |
| Test | JUnit 5, Mockito, Podam, Gson | — |
| Konteyner | Docker (eclipse-temurin:25-jre-alpine) | — |

## Mimari

Proje katmanlı mimari ile yapılandırılmıştır:

```
src/main/java/org/mesutormanli/customerapi/
├── config/
│   └── PersistenceConfig.java        # JPA / SQLite yapılandırması
├── controller/
│   ├── CustomerController.java       # Müşteri CRUD REST endpointleri
│   └── IndexController.java          # Kök uç noktası (HATEOAS bağlantısı)
├── model/
│   ├── converter/
│   │   └── CustomerConverter.java    # Entity ↔ DTO / Request dönüşümleri
│   ├── dto/
│   │   └── CustomerDto.java          # Veri transfer nesnesi (record)
│   ├── entity/
│   │   └── CustomerEntity.java       # JPA varlığı (soft-delete ile)
│   ├── hateoas/
│   │   └── CustomerControllerRepresentationModel.java
│   ├── request/
│   │   └── CustomerRequest.java      # İstek gövdesi (record)
│   └── response/
│       ├── CustomerDeleteResponse.java
│       └── CustomerListResponse.java
├── repository/
│   └── CustomerRepository.java       # JPA Repository arayüzü
└── service/
    ├── CustomerService.java          # Servis arayüzü
    └── impl/
        └── CustomerServiceImpl.java  # Servis gerçeklemesi
```

### Katman Sorumlulukları

- **Controller** — HTTP isteklerini karşılar, yanıtları döner.
- **Service** — İş mantığını yürütür, repository ve converter'ı orchestre eder.
- **Repository** — Spring Data JPA sayesinde CRUD işlemlerini otomatik olarak sağlar.
- **Entity** — `CUSTOMER` tablosuna eşlenen JPA varlığı.
- **Converter** — Entity, DTO ve Request arasında dönüşüm yapar.
- **Config** — SQLite DataSource ve Hibernate ayarlarını yapar.

## API Uç Noktaları

Tüm uç noktalar `/customer-api` context path'i altında çalışır.

| Yöntem | Uç Nokta | Açıklama | İstek | Yanıt | Durum Kodları |
|---|---|---|---|---|---|
| `GET` | `/` | Kök dizin, HATEOAS bağlantısı döner | — | HATEOAS model | `200 OK` |
| `GET` | `/customer/{id}` | ID'ye göre müşteri getirir | — | `CustomerListResponse` | `200 OK`, `404 Not Found` |
| `GET` | `/customers` | Tüm müşterileri listeler | — | `CustomerListResponse` | `200 OK` |
| `POST` | `/customer` | Yeni müşteri oluşturur | `CustomerRequest` | `CustomerDto` | `201 Created` |
| `PUT` | `/customer/{id}` | Müşteriyi günceller | `CustomerRequest` | `CustomerDto` | `200 OK`, `404 Not Found` |
| `DELETE` | `/customer/{id}` | Müşteriyi soft-delete eder | — | `CustomerDeleteResponse` | `200 OK`, `204 No Content` |
| `DELETE` | `/customers` | Tüm müşterileri soft-delete eder | — | `CustomerDeleteResponse` | `200 OK`, `204 No Content` |

### İstek/ Yanıt Örnekleri

**Müşteri Oluşturma (POST /customer-api/customer)**

```json
{
  "name": "Ahmet",
  "surname": "Yılmaz",
  "age": 30,
  "address": "İstanbul, Türkiye",
  "telephone": "+90 555 123 45 67",
  "email": "ahmet@ornek.com",
  "nationality": "Türk",
  "maritalStatus": "Bekar"
}
```

**Başarılı Yanıt (201 Created)**

```json
{
  "id": 1,
  "name": "Ahmet",
  "surname": "Yılmaz",
  "age": 30,
  "address": "İstanbul, Türkiye",
  "telephone": "+90 555 123 45 67",
  "email": "ahmet@ornek.com",
  "nationality": "Türk",
  "maritalStatus": "Bekar"
}
```

### Actuator Uç Noktaları

| Uç Nokta | Açıklama |
|---|---|
| `/actuator/health` | Uygulama sağlık kontrolü |
| `/actuator/info` | Uygulama bilgisi |
| `/actuator/logfile` | Log dosyası |
| `/actuator/metrics` | Metrikler |

## Veri Modeli

### CUSTOMER Tablosu

| Sütun | Tür | Açıklama |
|---|---|---|
| `id` | `BIGINT` | Birincil anahtar (oto-oluşturulur) |
| `name` | `VARCHAR` | Müşteri adı |
| `surname` | `VARCHAR` | Müşteri soyadı |
| `age` | `INTEGER` | Yaş |
| `address` | `VARCHAR` | Adres |
| `telephone` | `VARCHAR` | Telefon numarası |
| `email` | `VARCHAR` | E-posta adresi |
| `nationality` | `VARCHAR` | Uyruk |
| `maritalStatus` | `VARCHAR` | Medeni durum |
| `deleted` | `BOOLEAN` | Soft-delete bayrağı (varsayılan: false) |

### Soft Delete

Silme işlemleri fiziksel silme yerine mantıksal silme (soft-delete) olarak gerçekleştirilir:
- `DELETE` sorguları `UPDATE CUSTOMER SET deleted = true WHERE id=?` şeklinde dönüştürülür.
- Sorgular varsayılan olarak silinmiş kayıtları filtreler (`@SQLRestriction`).
- Bu sayede veri kaybı önlenir ve veri kurtarma mümkün olur.

## Kurulum

### Gereksinimler

- **Java 25** (JDK, preview özellikleri etkin)
- **Maven 3.8+** (veya Maven Wrapper)
- **Docker** (isteğe bağlı, konteyner dağıtımı için)

### Ortam Değişkenleri

Proje herhangi bir ortam değişkeni gerektirmez. Tüm yapılandırma `.properties` dosyalarında yapılır.

### Yapılandırma

**`src/main/resources/application.properties`** — Sunucu, hata yönetimi, loglama ve Actuator ayarları.

**`src/main/resources/persistence.properties`** — SQLite bağlantısı, Hibernate dialekt ve şema yönetimi.

SQLite veritabanı dosyası (`CustomerDB.sqlite`) otomatik olarak proje kök dizininde oluşturulur.

## Çalıştırma

### Maven ile

```bash
# Derleme
mvn clean compile

# Paketleme
mvn package

# Çalıştırma
java --enable-preview -jar target/customer-api-0.0.1-SNAPSHOT.jar
```

### Maven Wrapper ile

```bash
./mvnw clean package
java -jar target/customer-api-0.0.1-SNAPSHOT.jar
```

### VS Code ile

`.vscode/launch.json` dosyasında tanımlı "Customer API" konfigürasyonu ile çalıştırabilirsiniz.

### IntelliJ IDEA ile

Projeyi Maven projesi olarak içe aktarın ve `CustomerApiApplication.main()` metodunu çalıştırın.

## Test

```bash
mvn test
```

Testler şu kütüphaneleri kullanır:
- **JUnit 5** — Test framework'ü
- **Mockito** — Mock nesneler
- **Podam** — Rastgele test verisi üretimi
- **Gson** — JSON serileştirme
- **Spring REST Docs MockMvc** — API dokümantasyon snippet'leri

Test sınıfları:
- `BaseControllerTest` — Controller testleri için temel sınıf
- `BaseServiceTest` — Servis testleri için temel sınıf
- `CustomerControllerTest` — Müşteri controller testleri (REST Docs ile)
- `CustomerServiceTest` — Servis katmanı birim testleri
- `CustomerEntityTest`, `CustomerRequestTest`, `CustomerConverterTest` vb. — Model testleri

## Docker

```bash
# İmajı oluşturma
docker build -t customer-api .

# Konteynerı çalıştırma
docker run -p 8080:8080 customer-api
```

Docker imajı `eclipse-temurin:25-jre-alpine` tabanlıdır ve JAR dosyasını çalıştırır.

## CI/CD

GitHub Actions ile sürekli entegrasyon sağlanır:

| İş Akışı | Tetikleyici | Açıklama |
|---|---|---|
| **Java CI with Maven** (`maven.yml`) | `master` branch'ine push/PR | JDK 25 ile Maven build |
| **CodeQL** (`codeql.yml`) | `master` branch'ine push/PR, haftalık | Güvenlik analizi |
| **Qodana** (`qodana_code_quality.yml`) | `master` branch'ine push/PR | Kod kalitesi analizi |

## Proje Yapısı

```
customer-api/
├── .github/workflows/     # GitHub Actions CI/CD
├── .mvn/wrapper/          # Maven Wrapper
├── .vscode/               # VS Code konfigürasyonları
├── src/
│   ├── main/
│   │   ├── java/.../      # Kaynak kod
│   │   └── resources/     # Yapılandırma dosyaları
│   └── test/java/.../     # Test kodları
├── logs/                  # Uygulama logları
├── Dockerfile             # Docker imaj tanımı
├── pom.xml                # Maven proje tanımı
├── qodana.yaml            # Qodana kod kalite ayarları
├── LICENSE.txt            # GPL-3.0 lisansı
└── CustomerDB.sqlite      # SQLite veritabanı (çalışma zamanında oluşur)
```

## Katkıda Bulunma

1. Bu depoyu fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/yeni-ozellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/yeni-ozellik`)
5. Bir Pull Request oluşturun

## Lisans

Bu proje **GNU General Public License v3.0 (GPL-3.0)** ile lisanslanmıştır. Detaylar için `LICENSE.txt` dosyasına bakın.

---

**Geliştirici:** Mesut Ormanlı
