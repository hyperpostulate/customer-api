# customer-api

[![Java CI](https://github.com/hyperpostulate/customer-api/actions/workflows/maven.yml/badge.svg)](https://github.com/hyperpostulate/customer-api/actions/workflows/maven.yml) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

REST API for a SQLite Database. Spring Boot application with JPA, HATEOAS, and soft-delete support.

---

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Build & Test](#build--test)
- [CI/CD](#cicd)
- [Architecture](#architecture)
- [Components](#components)
- [Usage Examples](#usage-examples)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Error Handling](#error-handling)
- [License](#license)
- [Developer](#developer)
- [Contributing](#contributing)

---

## Requirements

| Requirement | Version |
|-------------|---------|
| Java | 25+ |
| Maven | 3.8+ |

---

## Installation

### Maven

```xml
<dependency>
    <groupId>org.mesutormanli</groupId>
    <artifactId>customer-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Manual Build

```bash
git clone https://github.com/hyperpostulate/customer-api.git
cd customer-api
mvn clean install
```

---

## Build & Test

```bash
mvn clean test                    # Full build + tests
mvn clean package                 # Build + tests + package
mvn test -Dtest=CustomerControllerTest  # Single test class
mvn test -Dtest=CustomerServiceTest     # Single test class
```

### Docker

```bash
mvn clean package -DskipTests         # Build JAR
docker build -t customer-api .       # Build image
docker run -p 8080:8080 customer-api  # Run container
```

The API will be available at `http://localhost:8080/customer-api`.

```bash
docker run -d -p 8080:8080 --name customer-api customer-api  # Run in background
docker stop customer-api              # Stop container
docker rm customer-api                # Remove container
```

---

## CI/CD

GitHub Actions workflows (`.github/workflows/`):

- **maven.yml**: Runs on every push. Ubuntu-latest, Amazon Corretto 25. Command: `mvn -B package`
- **codeql.yml**: CodeQL security analysis
- **qodana_code_quality.yml**: Qodana code quality inspection

---

## Architecture

```
org.mesutormanli.customerapi
├── config/
│   ├── DataSourceConfig.java              # SQLite DataSource bean
│   └── JpaConfig.java                     # JPA EntityManagerFactory + repos config
├── controller/
│   ├── CustomerController.java            # REST endpoints for CRUD
│   ├── CustomerResponseMapper.java        # Maps service results to ResponseEntity
│   └── IndexController.java               # Root "/" HATEOAS index endpoint
├── model/
│   ├── converter/
│   │   ├── CustomerReadConverter.java     # Entity -> DTO
│   │   └── CustomerWriteConverter.java    # Request -> Entity
│   ├── dto/
│   │   └── CustomerDto.java               # Java record, transfer object
│   ├── entity/
│   │   └── CustomerEntity.java            # JPA @Entity, table CUSTOMER
│   ├── hateoas/
│   │   └── CustomerControllerRepresentationModel.java
│   ├── request/
│   │   └── CustomerRequest.java           # Java record, inbound request body
│   └── response/
│       ├── CustomerDeleteResponse.java    # Java record, delete response
│       └── CustomerListResponse.java      # Java record, list response
├── repository/
│   └── CustomerRepository.java            # Spring Data JPA interface
├── service/
│   ├── CustomerService.java               # Service interface
│   └── impl/
│       └── CustomerServiceImpl.java       # Service implementation
└── CustomerApiApplication.java            # Application entry point
```

### Core Components

| Component | Description |
|-----------|-------------|
| `CustomerController` | REST controller providing full CRUD for customers |
| `CustomerService` | Service interface defining business operations |
| `CustomerServiceImpl` | Service implementation with soft-delete support |
| `CustomerRepository` | Spring Data JPA repository for `CustomerEntity` |
| `CustomerEntity` | JPA entity with soft-delete via `@SQLDelete` |
| `CustomerDto` | Immutable Java record for data transfer |
| `CustomerRequest` | Java record for inbound request body |
| `CustomerResponseMapper` | Maps service results to ResponseEntity with HTTP status |
| `DataSourceConfig` | Reads `persistence.properties`, creates SQLite DataSource |
| `JpaConfig` | Creates EntityManagerFactory with Hibernate SQLite dialect |
| `CustomerReadConverter` | Converts Entity to DTO |
| `CustomerWriteConverter` | Converts Request to Entity |

### Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| Spring Boot Web | 4.1.0 | REST/MVC framework |
| Spring Boot Data JPA | 4.1.0 | JPA + Hibernate ORM |
| Spring Boot HATEOAS | 4.1.0 | HATEOAS hypermedia |
| Spring Boot Security | 4.1.0 | Security auto-configuration |
| Spring Boot Actuator | 4.1.0 | Health/metrics endpoints |
| Spring Boot Validation | 4.1.0 | Bean validation |
| SQLite JDBC | 3.46.0.0 | SQLite JDBC driver |
| Hibernate Community Dialects | - | Hibernate SQLite dialect |
| PODAM | 7.2.11.RELEASE | Random test data generation (test) |
| Gson | 2.11.0 | JSON serialization (test) |
| Spring REST Docs MockMvc | - | REST API documentation (test) |
| JUnit Jupiter | - | Test framework (test) |

---

## Components

### CustomerController

REST controller providing full CRUD operations for customers.

| Method | Endpoint | Description | HTTP Status |
|--------|----------|-------------|-------------|
| `GET` | `/customer/{id}` | Get a single customer by ID | 200 / 404 |
| `GET` | `/customers` | Get all customers | 200 |
| `POST` | `/customer` | Create a new customer | 201 |
| `PUT` | `/customer/{id}` | Update a customer by ID | 200 / 404 |
| `DELETE` | `/customer/{id}` | Soft-delete a customer by ID | 204 / 404 |
| `DELETE` | `/customers` | Delete all customers | 204 |

### CustomerEntity

JPA entity mapped to the `CUSTOMER` table with soft-delete support.

| Field | Type | Description |
|-------|------|-------------|
| `id` | `Long` | Auto-generated primary key |
| `name` | `String` | Customer name |
| `surname` | `String` | Customer surname |
| `age` | `Integer` | Customer age |
| `address` | `String` | Customer address |
| `telephone` | `String` | Customer telephone |
| `email` | `String` | Customer email |
| `nationality` | `String` | Customer nationality |
| `maritalStatus` | `String` | Customer marital status |
| `deleted` | `Boolean` | Soft-delete flag |

### IndexController

Returns a HATEOAS `RepresentationModel` with a link to the `CustomerController`, serving as the API's discovery root.

---

## Usage Examples

### Creating a Customer

```bash
curl -X POST http://localhost:8080/customer-api/customer \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John",
    "surname": "Doe",
    "age": 30,
    "address": "123 Main St",
    "telephone": "+905551234567",
    "email": "john.doe@example.com",
    "nationality": "Turkish",
    "maritalStatus": "Single"
  }'
```

### Getting All Customers

```bash
curl http://localhost:8080/customer-api/customers
```

### Getting a Customer by ID

```bash
curl http://localhost:8080/customer-api/customer/1
```

### Updating a Customer

```bash
curl -X PUT http://localhost:8080/customer-api/customer/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John",
    "surname": "Doe",
    "age": 31,
    "address": "456 Oak Ave",
    "telephone": "+905559876543",
    "email": "john.doe@example.com",
    "nationality": "Turkish",
    "maritalStatus": "Married"
  }'
```

### Deleting a Customer

```bash
curl -X DELETE http://localhost:8080/customer-api/customer/1
```

### Deleting All Customers

```bash
curl -X DELETE http://localhost:8080/customer-api/customers
```

### Accessing the API Index (HATEOAS)

```bash
curl http://localhost:8080/customer-api/
```

---

## API Endpoints

All endpoints are served under the context path `/customer-api`.

| Endpoint | Method | Description | Response |
|----------|--------|-------------|----------|
| `/customer/{id}` | GET | Get customer by ID | `CustomerListResponse` |
| `/customers` | GET | Get all customers | `CustomerListResponse` |
| `/customer` | POST | Create new customer | `CustomerListResponse` (201) |
| `/customer/{id}` | PUT | Update customer | `CustomerListResponse` |
| `/customer/{id}` | DELETE | Soft-delete customer | `CustomerDeleteResponse` (204) |
| `/customers` | DELETE | Delete all customers | 204 |
| `/` | GET | API discovery root (HATEOAS) | `RepresentationModel` |

### Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/customer-api/actuator/health` | Application health status |
| `/customer-api/actuator/info` | Application info |
| `/customer-api/actuator/logfile` | Application log file |
| `/customer-api/actuator/metrics` | Application metrics |

---

## Testing

All tests use `@WebMvcTest` for controller tests and `@ExtendWith(SpringExtension.class)` for service tests. Test data is generated using PODAM via `GenericMockDataBuilder`.

### Test Classes

| Test Class | What It Tests |
|------------|---------------|
| `CustomerControllerTest` | All 8 controller scenarios (success + not-found for each operation) |
| `IndexControllerTest` | Root `/` endpoint returns HTTP 200 |
| `PersistenceConfigTest` | DataSourceConfig creates correct DataSource, JpaConfig creates correct EntityManagerFactory |
| `CustomerServiceTest` | All 10 service-layer scenarios (CRUD success + not-found/empty cases) |
| `CustomerConverterTest` | Null-input handling for both converters |
| `CustomerEntityTest` | Getters, builder, equals/hashCode, toString |
| `CustomerRequestTest` | No-arg constructor and builder |
| `CustomerDeleteResponseTest` | No-arg constructor and builder |
| `CustomerListResponseTest` | No-arg constructor and builder |

### Running Tests

```bash
# All tests
mvn test

# Single test class
mvn test -Dtest=CustomerControllerTest

# Single test method
mvn test -Dtest=CustomerControllerTest#testGetCustomerById
```

### Test Helpers

`BaseControllerTest` provides helper methods for controller tests:

```java
protected void verifyResult(MockMvc mockMvc, String url, int expectedStatus) throws Exception {
    mockMvc.perform(get(url))
           .andExpect(status().isOk());
}
```

`CustomerMockDataBuilder` provides static factory methods to produce test data:

```java
CustomerDto dto = CustomerMockDataBuilder.aCustomerDto().build();
CustomerRequest request = CustomerMockDataBuilder.aCustomerRequest().build();
```

---

## Error Handling

All HTTP and parse errors throw standard Spring exceptions. The `CustomerResponseMapper` translates service-layer return values into properly typed `ResponseEntity` objects with appropriate HTTP status codes:

| Scenario | HTTP Status |
|----------|-------------|
| Resource found | 200 OK |
| Resource created | 201 Created |
| Resource deleted | 204 No Content |
| Resource not found | 404 Not Found |

---

## License

This project is licensed under the GNU General Public License v3.0. See [LICENSE.txt](LICENSE.txt) for details.

---

## Developer

**Mesut ORMANLI**

- Email: [mesutormanli@gmail.com](mailto:mesutormanli@gmail.com)
- GitHub: [@hyperpostulate](https://github.com/hyperpostulate)

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

---

---

---

# customer-api

[![Java CI](https://github.com/hyperpostulate/customer-api/actions/workflows/maven.yml/badge.svg)](https://github.com/hyperpostulate/customer-api/actions/workflows/maven.yml) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

SQLite Veritabanı için REST API. Spring Boot, JPA, HATEOAS ve soft-delete desteği ile oluşturulmuş uygulama.

---

## İçindekiler

- [Gereksinimler](#gereksinimler)
- [Kurulum](#kurulum)
- [Derleme ve Test](#derleme-ve-test)
- [CI/CD](#cicd)
- [Mimari](#mimari)
- [Bileşenler](#bileşenler)
- [Kullanım Örnekleri](#kullanım-örnekleri)
- [API Uç Noktaları](#api-uç-noktaları)
- [Testler](#testler)
- [Hata Yönetimi](#hata-yönetimi)
- [Lisans](#lisans)
- [Geliştirici](#geliştirici)
- [Katkıda Bulunma](#katkıda-bulunma)

---

## Gereksinimler

| Gereksinim | Sürüm |
|------------|-------|
| Java | 25+ |
| Maven | 3.8+ |

---

## Kurulum

### Maven

```xml
<dependency>
    <groupId>org.mesutormanli</groupId>
    <artifactId>customer-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Manuel Derleme

```bash
git clone https://github.com/hyperpostulate/customer-api.git
cd customer-api
mvn clean install
```

---

## Derleme ve Test

```bash
mvn clean test                    # Tam derleme + testler
mvn clean package                 # Derleme + test + paketleme
mvn test -Dtest=CustomerControllerTest  # Tek test sınıfı
mvn test -Dtest=CustomerServiceTest     # Tek test sınıfı
```

### Docker

```bash
mvn clean package -DskipTests         # JAR'ı derle
docker build -t customer-api .       # İmajı oluştur
docker run -p 8080:8080 customer-api  # Konteyneri çalıştır
```

API `http://localhost:8080/customer-api` adresinde kullanılabilir olur.

```bash
docker run -d -p 8080:8080 --name customer-api customer-api  # Arka planda çalıştır
docker stop customer-api              # Konteyneri durdur
docker rm customer-api                # Konteyneri kaldır
```

---

## CI/CD

GitHub Actions iş akışları (`.github/workflows/`):

- **maven.yml**: Her push'ta çalışır. Ubuntu-latest, Amazon Corretto 25. Komut: `mvn -B package`
- **codeql.yml**: CodeQL güvenlik analizi
- **qodana_code_quality.yml**: Qodana kod kalitesi incelemesi

---

## Mimari

```
org.mesutormanli.customerapi
├── config/
│   ├── DataSourceConfig.java              # SQLite DataSource bean
│   └── JpaConfig.java                     # JPA EntityManagerFactory + repo yapılandırması
├── controller/
│   ├── CustomerController.java            # CRUD için REST uç noktaları
│   ├── CustomerResponseMapper.java        # Servis sonuçlarını ResponseEntity'e dönüştürür
│   └── IndexController.java               # Kök "/" HATEOAS indeks uç noktası
├── model/
│   ├── converter/
│   │   ├── CustomerReadConverter.java     # Entity -> DTO
│   │   └── CustomerWriteConverter.java    # Request -> Entity
│   ├── dto/
│   │   └── CustomerDto.java               # Java record, veri aktarım nesnesi
│   ├── entity/
│   │   └── CustomerEntity.java            # JPA @Entity, CUSTOMER tablosu
│   ├── hateoas/
│   │   └── CustomerControllerRepresentationModel.java
│   ├── request/
│   │   └── CustomerRequest.java           # Java record, gelen istek gövdesi
│   └── response/
│       ├── CustomerDeleteResponse.java    # Java record, silme yanıtı
│       └── CustomerListResponse.java      # Java record, listeleme yanıtı
├── repository/
│   └── CustomerRepository.java            # Spring Data JPA arayüzü
├── service/
│   ├── CustomerService.java               # Servis arayüzü
│   └── impl/
│       └── CustomerServiceImpl.java       # Servis uygulaması
└── CustomerApiApplication.java            # Uygulama giriş noktası
```

### Temel Bileşenler

| Bileşen | Açıklama |
|---------|----------|
| `CustomerController` | Müşteriler için tam CRUD sağlayan REST kontrolcüsü |
| `CustomerService` | İş operasyonlarını tanımlayan servis arayüzü |
| `CustomerServiceImpl` | Soft-delete desteğiyle servis uygulaması |
| `CustomerRepository` | `CustomerEntity` için Spring Data JPA deposu |
| `CustomerEntity` | `@SQLDelete` ile soft-delete destekli JPA entity'si |
| `CustomerDto` | Veri transferi için değişmez Java record |
| `CustomerRequest` | Gelen istek gövdesi için Java record |
| `CustomerResponseMapper` | Servis sonuçlarını HTTP durum kodlarıyla ResponseEntity'e dönüştürür |
| `DataSourceConfig` | `persistence.properties` dosyasını okur, SQLite DataSource oluşturur |
| `JpaConfig` | Hibernate SQLite diyalekti ile EntityManagerFactory oluşturur |
| `CustomerReadConverter` | Entity'yi DTO'ya dönüştürür |
| `CustomerWriteConverter` | Request'i Entity'ye dönüştürür |

### Bağımlılıklar

| Bağımlılık | Sürüm | Amaç |
|------------|-------|------|
| Spring Boot Web | 4.1.0 | REST/MVC çerçevesi |
| Spring Boot Data JPA | 4.1.0 | JPA + Hibernate ORM |
| Spring Boot HATEOAS | 4.1.0 | HATEOAS hipermedya |
| Spring Boot Security | 4.1.0 | Güvenlik otomatik yapılandırması |
| Spring Boot Actuator | 4.1.0 | Sağlık/metrik uç noktaları |
| Spring Boot Validation | 4.1.0 | Bean doğrulama |
| SQLite JDBC | 3.46.0.0 | SQLite JDBC sürücüsü |
| Hibernate Community Dialects | - | Hibernate SQLite diyalekti |
| PODAM | 7.2.11.RELEASE | Rastgele test verisi üretimi (test) |
| Gson | 2.11.0 | JSON serileştirme (test) |
| Spring REST Docs MockMvc | - | REST API dokümantasyonu (test) |
| JUnit Jupiter | - | Test çerçevesi (test) |

---

## Bileşenler

### CustomerController

Müşteriler için tam CRUD işlemleri sağlayan REST kontrolcüsü.

| Yöntem | Uç Nokta | Açıklama | HTTP Durumu |
|--------|----------|----------|-------------|
| `GET` | `/customer/{id}` | ID ile tek müşteri getir | 200 / 404 |
| `GET` | `/customers` | Tüm müşterileri getir | 200 |
| `POST` | `/customer` | Yeni müşteri oluştur | 201 |
| `PUT` | `/customer/{id}` | ID ile müşteriyi güncelle | 200 / 404 |
| `DELETE` | `/customer/{id}` | ID ile müşteriyi soft-delete | 204 / 404 |
| `DELETE` | `/customers` | Tüm müşterileri sil | 204 |

### CustomerEntity

Soft-delete desteğiyle `CUSTOMER` tablosuna eşlenen JPA entity'si.

| Alan | Tür | Açıklama |
|------|-----|----------|
| `id` | `Long` | Otomatik üretilen birincil anahtar |
| `name` | `String` | Müşteri adı |
| `surname` | `String` | Müşteri soyadı |
| `age` | `Integer` | Müşteri yaşı |
| `address` | `String` | Müşteri adresi |
| `telephone` | `String` | Müşteri telefonu |
| `email` | `String` | Müşteri e-postası |
| `nationality` | `String` | Müşteri uyruğu |
| `maritalStatus` | `String` | Müşteri medeni durumu |
| `deleted` | `Boolean` | Soft-delete bayrağı |

### IndexController

API'nin keşif kökü olarak hizmet veren, `CustomerController`'a bağlantı içeren bir HATEOAS `RepresentationModel` döndürür.

---

## Kullanım Örnekleri

### Müşteri Oluşturma

```bash
curl -X POST http://localhost:8080/customer-api/customer \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John",
    "surname": "Doe",
    "age": 30,
    "address": "123 Main St",
    "telephone": "+905551234567",
    "email": "john.doe@example.com",
    "nationality": "Turkish",
    "maritalStatus": "Single"
  }'
```

### Tüm Müşterileri Getirme

```bash
curl http://localhost:8080/customer-api/customers
```

### ID ile Müşteri Getirme

```bash
curl http://localhost:8080/customer-api/customer/1
```

### Müşteri Güncelleme

```bash
curl -X PUT http://localhost:8080/customer-api/customer/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John",
    "surname": "Doe",
    "age": 31,
    "address": "456 Oak Ave",
    "telephone": "+905559876543",
    "email": "john.doe@example.com",
    "nationality": "Turkish",
    "maritalStatus": "Married"
  }'
```

### Müşteri Silme

```bash
curl -X DELETE http://localhost:8080/customer-api/customer/1
```

### Tüm Müşterileri Silme

```bash
curl -X DELETE http://localhost:8080/customer-api/customers
```

### API İndeksine Erişme (HATEOAS)

```bash
curl http://localhost:8080/customer-api/
```

---

## API Uç Noktaları

Tüm uç noktalar `/customer-api` bağlam yolu altında sunulur.

| Uç Nokta | Yöntem | Açıklama | Yanıt |
|----------|--------|----------|-------|
| `/customer/{id}` | GET | ID ile müşteri getir | `CustomerListResponse` |
| `/customers` | GET | Tüm müşterileri getir | `CustomerListResponse` |
| `/customer` | POST | Yeni müşteri oluştur | `CustomerListResponse` (201) |
| `/customer/{id}` | PUT | Müşteriyi güncelle | `CustomerListResponse` |
| `/customer/{id}` | DELETE | Müşteriyi soft-delete | `CustomerDeleteResponse` (204) |
| `/customers` | DELETE | Tüm müşterileri sil | 204 |
| `/` | GET | API keşif kökü (HATEOAS) | `RepresentationModel` |

### Actuator Uç Noktaları

| Uç Nokta | Açıklama |
|----------|----------|
| `/customer-api/actuator/health` | Uygulama sağlık durumu |
| `/customer-api/actuator/info` | Uygulama bilgisi |
| `/customer-api/actuator/logfile` | Uygulama log dosyası |
| `/customer-api/actuator/metrics` | Uygulama metrikleri |

---

## Testler

Tüm testler kontrolcü testleri için `@WebMvcTest` ve servis testleri için `@ExtendWith(SpringExtension.class)` kullanır. Test verileri PODAM kullanılarak `GenericMockDataBuilder` aracılığıyla üretilir.

### Test Sınıfları

| Test Sınıfı | Ne Test Eder |
|-------------|--------------|
| `CustomerControllerTest` | 8 kontrolcü senaryosunun tamamı (her operasyon için başarı + bulunamadı) |
| `IndexControllerTest` | Kök `/` uç noktasının HTTP 200 döndürmesi |
| `PersistenceConfigTest` | DataSourceConfig'in doğru DataSource oluşturması, JpaConfig'in doğru EntityManagerFactory oluşturması |
| `CustomerServiceTest` | 10 servis katmanı senaryosunun tamamı (CRUD başarı + bulunamadı/boş durumlar) |
| `CustomerConverterTest` | Her iki dönüştürücü için null-girdi işleme |
| `CustomerEntityTest` | Getter'lar, builder, equals/hashCode, toString |
| `CustomerRequestTest` | Argümansız constructor ve builder |
| `CustomerDeleteResponseTest` | Argümansız constructor ve builder |
| `CustomerListResponseTest` | Argümansız constructor ve builder |

### Test Çalıştırma

```bash
# Tüm testler
mvn test

# Tek test sınıfı
mvn test -Dtest=CustomerControllerTest

# Tek test metodu
mvn test -Dtest=CustomerControllerTest#testGetCustomerById
```

### Test Yardımcıları

`BaseControllerTest`, kontrolcü testleri için yardımcı metodlar sağlar:

```java
protected void verifyResult(MockMvc mockMvc, String url, int expectedStatus) throws Exception {
    mockMvc.perform(get(url))
           .andExpect(status().isOk());
}
```

`CustomerMockDataBuilder`, test verileri üretmek için statik fabrika metodları sağlar:

```java
CustomerDto dto = CustomerMockDataBuilder.aCustomerDto().build();
CustomerRequest request = CustomerMockDataBuilder.aCustomerRequest().build();
```

---

## Hata Yönetimi

Tüm HTTP ve parse hataları standart Spring istisnaları fırlatır. `CustomerResponseMapper`, servis katmanı sonuçlarını uygun HTTP durum kodlarıyla doğru türde `ResponseEntity` nesnelerine dönüştürür:

| Senaryo | HTTP Durumu |
|---------|-------------|
| Kaynak bulundu | 200 OK |
| Kaynak oluşturuldu | 201 Created |
| Kaynak silindi | 204 No Content |
| Kaynak bulunamadı | 404 Not Found |

---

## Lisans

Bu proje GNU General Public License v3.0 altında lisanslanmıştır. Detaylı bilgi için [LICENSE.txt](LICENSE.txt) dosyasına bakın.

---

## Geliştirici

**Mesut ORMANLI**

- E-posta: [mesutormanli@gmail.com](mailto:mesutormanli@gmail.com)
- GitHub: [@hyperpostulate](https://github.com/hyperpostulate)

---

## Katkıda Bulunma

1. Depoyu fork edin
2. Bir özellik dalı oluşturun (`git checkout -b feature/new-feature`)
3. Değişikliklerinizi Commit edin (`git commit -m 'Add new feature'`)
4. Dalı itin (`git push origin feature/new-feature`)
5. Bir Pull Request oluşturun
