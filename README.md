# University-Domain

Find University is a user-friendly app that helps you discover universities worldwide. Simply enter a country name to see a list of universities, and tap on any to view detailed information, including names, countries, and web links. Tap a link to open it in your browser.

## Tech stack & Open-source libraries

- **Minimum SDK level**: 24
- **Language**: 100% Kotlin

### Architecture

- **MVVM Architecture**
  - View: Utilizes View Binding to bind UI components to data sources in your app using a declarative approach.
  - View Model: Manages UI-related data in a lifecycle-conscious way.
  - Model: Represents the data and business logic of the application.

- **Dependencies**
  - **Dependency Injection**: Koin
  - **Asynchronous Programming**: Flow
  - **Network Communication**: Retrofit
  - **Navigation**: Jetpack Navigation
  - **Design**: Material Design

## SEARCH FROM OUR PUBLIC API

You can search universities using the following public API:
- [Universities API](http://universities.hipolabs.com)
- Example search endpoint: [Search universities in Pakistan](http://universities.hipolabs.com/search?country=pakistan)

## API Search Endpoint

### Request
- /search?country=pakistan


### Response

```json
[
  {
    "domains": ["itu.edu.pk"],
    "country": "Pakistan",
    "alpha_two_code": "PK",
    "web_pages": ["https://itu.edu.pk/"],
    "name": "Information Technology University, Lahore",
    "state-province": "Punjab"
  },
  {
    "domains": ["abasyn.edu.pk"],
    "country": "Pakistan",
    "alpha_two_code": "PK",
    "web_pages": ["http://www.abasyn.edu.pk/"],
    "name": "Abasyn University Peshawar",
    "state-province": "Khyber Pakhtunkhwa"
  }
] ```


## Run the Project
 - git clone https://github.com/MehranAli0312/University-Domain.git
 - Open the project in Android Studio.
 - Build and run on an Android device or emulator.

## Contribution
Please contribute to this list! We need your support to keep this list up-to-date. If you find any incorrect data, feel free to fix it by opening a pull request or creating an issue.
