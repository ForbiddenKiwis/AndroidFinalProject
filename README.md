# Fitness Application

This repository contains the source code for a Fitness Application built for Android as part of a final project. The app combines user authentication with a Body Mass Index (BMI) tracking system to provide users with a simple and effective way to monitor their fitness progression.

## Features

### 1. User Authentication System
The application includes a robust user authentication system that supports:
- **Account Creation**: New users can register with their email and password.
- **Login**: Existing users can log in securely.
- **Logout**: Users can log out of their accounts.
- **Account Deletion**: Users can delete their accounts if desired.
- **Session Management**: A cookie-based session system is implemented, automatically logging users out after 5 minutes of inactivity to ensure security.

### 2. BMI System
The application allows users to:
- Calculate their Body Mass Index (BMI) based on their height and weight.
- View their BMI history and track changes over time.

## Technologies Used

- **Android JDK**: The app is developed using Java and Android SDK tools.
- **Firebase**: Firebase is used as the backend database to store user information, BMI records, and manage authentication.

## Use Case
The Fitness Application is designed for individuals who want to monitor their BMI and track their overall fitness progression. Whether you're aiming for weight management or just curious about your fitness stats, this app provides a straightforward and secure way to achieve your goals.

## How It Works
1. **Authentication**: Users can create an account or log in to access their data.
2. **BMI Calculation**: Users input their height and weight, and the app calculates their BMI.
3. **Session Management**: If a user is inactive for 5 minutes, their session will be cleared, and they will be redirected to the login page.

## Installation
1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```
2. Open the project in Android Studio.
3. Connect to Firebase by adding your `google-services.json` file to the app directory.
4. Build and run the application on an Android device or emulator.

## Future Enhancements
- **Fitness Progress Chart**: Visualize BMI changes over time with a graphical representation.
- **Workout Recommendations**: Provide fitness tips based on BMI.
- **Integration with Wearables**: Sync data from fitness trackers for more accurate statistics.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

---
Feel free to contribute or provide feedback to help improve this application!
