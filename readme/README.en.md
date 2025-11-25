# 📊 Matomo API Client for Java

**A lightweight, type-safe Java library to easily interact with the Matomo (Analytics) API.**

[Leer en Español](../README.md)

---

## 📚 Table of Contents

* [✨ Description](#-description)
* [⭐ Key Features](#-key-features)
* [🛠️ Installation](#️-installation)
* [⚙️ Configuration](#️-configuration)
* [▶️ Usage](#️-usage)
* [🤝 Contributing](#-contributing)
* [📄 License](#-license)
* [👤 Authors](#-authors)

---

## ✨ Description

This library allows **Java** applications to easily communicate with **Matomo**, making it simple to perform requests to its API in a **type-safe** and robust way. It is the ideal tool to integrate visit statistics, actions, and other analytical metrics directly into your Java projects.

---

## ⭐ Key Features

* **Compatibility:** Supports Matomo **v4** and **v5**.
* **Security:** Secure authentication using the `token_auth` parameter.
* **Type Safety:** Uses **strongly typed** response models for key modules (such as `VisitsSummary`), improving safety and code readability.
* **Error Handling:** Success and error management using the **Result pattern**, forcing explicit handling of both outcomes.
* **Easy Integration:** Simple to include in Java/Kotlin projects through **Gradle** or **Maven**.

---

## 🛠️ Installation

You can add this library to your project using Gradle or Maven.

### With Gradle

```gradle
dependencies {
    implementation 'com.javi:matomo-api-client:1.0.0'
}
```

### With Maven

```xml
<dependency>
    <groupId>com.javi</groupId>
    <artifactId>matomo-api-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

---

## ⚙️ Configuration

To get started, initialize the client with your **Matomo base URL**, `token_auth`, and the Matomo version you are using.

```java
 MatomoClient matomoClient = new MatomoClient.Builder()
        .baseUrl("https://demo.matomo.cloud") // Your Matomo URL
        .tokenAuth("anonymous") // Your token_auth (or 'anonymous' for public APIs)
        .matomoVersion(4) // Your Matomo version (e.g., 4 or 5)
        .build();
```

---

## ▶️ Usage

Below is a complete example to get the total number of visits (`GetVisits`) for today using the `VisitsSummary` module:

```java
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize the client
        MatomoClient matomoClient = new MatomoClient.Builder()
                .baseUrl("https://demo.matomo.cloud")
                .tokenAuth("anonymous")
                .matomoVersion(4)
                .build();

        LocalDate today = LocalDate.now();

        // 2. Perform the API call
        Result<VisitSummary.GetVisits, String> getVisits = matomoClient
                .visitsSummaryModule()
                .getVisits(
                    1, // idSite
                    PeriodEnum.DAY, // Period (DAY, WEEK, MONTH, YEAR, RANGE)
                    List.of(today), // Dates
                    null // Optional segment
                );

        // 3. Handle the result (Result pattern)
        if (getVisits.isSuccess()) {
            // Successful API response
            System.out.println("Today's visits: " + getVisits.getSuccessValue().getNb_visits());
        }
        if (getVisits.isFailure()) {
            // Error communicating with the API
            System.err.println("Error retrieving visits: " + getVisits.getError());
        }
    }
}
```

> **Note:** The type `Result<VisitSummary.GetVisits, String>` is key to safely and explicitly handling both success (typed value) and error (string message).

---

## 📅 Reason for Using `List<LocalDate>`

The design of using a date list was introduced for the following important reasons:

### 1. Direct Support for Date Ranges (`RANGE`)

The most complex case is when the period is **`RANGE`**. Matomo’s API requires the `date` parameter to be formatted as a pair of dates separated by a comma (`YYYY-MM-DD,YYYY-MM-DD`).

* **Implementation:** When receiving a list of dates (`dates`), the constructor can:

    1. Sort the list (`sortedDates`)
    2. Take the **earliest** date as the start
    3. Take the **latest** date as the end
    4. Build the `dateString` in the required `start,end` format

### 2. Constructor Consistency and Unification

If separate parameters were used (e.g., `LocalDate startDate`, `LocalDate endDate`), the constructor would become confusing for simple periods (`DAY`, `WEEK`, `MONTH`, `YEAR`) where only a single date is needed.

* **Unified Design:** By always requiring a `List<LocalDate>`, the constructor interface remains **consistent** regardless of the `PeriodEnum`:

    * **DAY/WEEK/MONTH/YEAR:** Pass a list containing a single date, and the code uses only the first one.
    * **RANGE:** Pass a list with at least two dates, and the code constructs the `start,end` range.

### 3. Flexibility and Error Checking

Using a list enables robust validations and clear logic inside the constructor:

* **Range Validation:**

  ```java
  if (period == PeriodEnum.RANGE && dates.size() < 2) {
      throw new IllegalArgumentException("Range period requires at least two dates");
  }
  ```
* **Order Handling:**
  The list is automatically sorted (`sortedDates.sort(Comparator.naturalOrder())`), ensuring the range always uses the earliest and latest dates, even if provided in reverse order.

---

## 🤝 Contributing

Contributions are welcome! If you want to improve the library, add new API modules, or fix bugs, please follow these steps:

1. Make a **fork** of the project.
2. Create a new branch for your feature or fix (e.g., `feature/new-endpoint`).
3. Ensure your changes include **unit tests** and the necessary **documentation**.
4. Create a **Pull Request**.

---

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for more details.

---

## 👤 Authors

* **Javier A. Roque Sañudo** – Lead developer.

    * GitHub: [@javicaib](https://github.com/javicaib)
