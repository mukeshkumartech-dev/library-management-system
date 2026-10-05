# Library Management System

A complete, production-quality full-stack Library Management System built as a virtual internship project.

## 1. Project Overview

This is a comprehensive web-based Library Management System that allows librarians/admins to manage books, members, book issuing/returning, and track library records.

It features a responsive React frontend, a robust Spring Boot backend, JWT authentication, and MySQL database integration.

## 2. Features

*   **Authentication & Authorization:** Secure login using JWT with role-based access control (Admin/Librarian).
*   **Dashboard:** Real-time statistics of total books, members, issued books, and overdue books.
*   **Book Management:** Complete CRUD operations for books with available/total copy tracking.
*   **Member Management:** Register and manage library members.
*   **Issue & Return Workflow:** 
    *   Issue books to members (decreases available copies).
    *   Return books (increases available copies, calculates fines).
*   **Search & Filtering:** Global search functionality for books and members.

## 3. Technology Stack

### Frontend
*   React.js 18 (TypeScript)
*   Vite
*   Tailwind CSS
*   React Router DOM
*   Axios for API requests
*   Lucide React (Icons)

### Backend
*   Java 21
*   Spring Boot 3.3.x
*   Spring Security & JWT (io.jsonwebtoken)
*   Spring Data JPA / Hibernate
*   Maven

### Database
*   MySQL 8.x

## 4. System Architecture

The application uses a standard 3-tier architecture:
1.  **Presentation Layer:** React SPA (Single Page Application) communicating via RESTful APIs.
2.  **Application Layer:** Spring Boot providing secure REST endpoints. Organized into Controllers, Services, and Repositories.
3.  **Data Layer:** MySQL database managed by Spring Data JPA.

## 5. Database Schema

The database consists of 4 main tables:

*   **`users`**: `id`, `username`, `password`, `role`, `status`, `created_at`
*   **`books`**: `id`, `isbn`, `title`, `author`, `category`, `publisher`, `total_copies`, `available_copies`, `shelf_number`, etc.
*   **`members`**: `id`, `membership_number`, `name`, `email`, `phone`, `status`, etc.
*   **`borrowings`**: `id`, `book_id`, `member_id`, `issue_date`, `due_date`, `return_date`, `status`, `fine_amount`

## 6. API Documentation

### Authentication
*   `POST /api/auth/login` - Authenticate and get JWT

### Books
*   `GET /api/books` - List all books
*   `GET /api/books/{id}` - Get book details
*   `POST /api/books` - Add new book
*   `PUT /api/books/{id}` - Update book
*   `DELETE /api/books/{id}` - Delete book
*   `GET /api/books/search?query={query}` - Search books

### Members
*   `GET /api/members` - List all members
*   `GET /api/members/{id}` - Get member details
*   `POST /api/members` - Add member
*   `PUT /api/members/{id}` - Update member
*   `DELETE /api/members/{id}` - Delete member

### Borrowings
*   `GET /api/borrowings` - List all borrowing transactions
*   `POST /api/borrowings/issue` - Issue a book to a member
*   `PUT /api/borrowings/{id}/return` - Return a book
*   `GET /api/borrowings/member/{id}` - Get borrowing history of a member

### Dashboard
*   `GET /api/dashboard/statistics` - Get dashboard stats

## 7. Installation & Configuration

### Prerequisites
*   JDK 21
*   Node.js 18+
*   MySQL 8+
*   Maven

### Database Setup
1. Create a MySQL database:
   ```sql
   CREATE DATABASE library_db;
   ```
2. Update the database credentials in `backend/src/main/resources/application.properties` (or set environment variables).

## 8. How to Run Backend

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will start on `http://localhost:8080`. (The database tables and initial sample data will be automatically generated upon startup).

## 9. How to Run Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
   The frontend will be available at `http://localhost:5173`.

## 10. Test Credentials

The backend automatically creates these users on startup if they don't exist:

**Admin Account:**
*   Username: `admin`
*   Password: `admin123`

**Librarian Account:**
*   Username: `librarian`
*   Password: `lib123`

## 11. Testing

The backend includes automated tests using JUnit and Mockito.
To run the tests:
```bash
cd backend
mvn test
```

## 12. Project Workflow

1. **Admin Login**: Admin logs in at `/login` using test credentials.
2. **Dashboard**: Admin views real-time statistics of books and members.
3. **Manage Books**: Admin adds new books or edits existing inventory.
4. **Manage Members**: Admin registers a new library member.
5. **Issue Book**: Admin selects an available book and an active member, setting a due date.
6. **Return Book**: When the member returns the book, Admin processes the return. Available copies increment, and any overdue fines are automatically calculated.

## 13. Future Enhancements

*   Member Portal for members to view their own history and reserve books.
*   Email notifications for due dates and overdue fines.
*   Barcode scanner integration for faster issuing/returning.
