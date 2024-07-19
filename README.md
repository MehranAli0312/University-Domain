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
]

## Screenshots

![Screenshot_2024-07-19-11-05-43-554_com example genesis](https://github.com/user-attachments/assets/34404e09-eb62-4c5c-9190-dbc350d5d381)

### if country name does't match:
![Screenshot_2024-07-19-11-06-11-448_com example genesis](https://github.com/user-attachments/assets/daa2a4d5-cc77-426b-9794-8a1d56f9ae99)

![Screenshot_2024-07-19-11-07-08-656_com google android apps maps](https://github.com/user-attachments/assets/6016ae36-ce1e-4f22-a4dc-a83743212d45)
![Screenshot_2024-07-19-11-06-49-967_com example genesis](https://github.com/user-attachments/assets/d4aa036f-6425-4ca1-8c61-1cf58e47ee66)
![Screenshot_2024-07-19-11-06-36-712_com example genesis](https://github.com/user-attachments/assets/d7a3645e-eca1-490b-ab9b-6497baaa4bc7)



### Run the Project
 - git clone https://github.com/MehranAli0312/University-Domain.git
 - Open the project in Android Studio.
 - Build and run on an Android device or emulator.

### Contribution
Please contribute to this list! We need your support to keep this list up-to-date. If you find any incorrect data, feel free to fix it by opening a pull request or creating an issue.
