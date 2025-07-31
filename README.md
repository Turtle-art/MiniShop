# 🛍️ MiniShop – E-Commerce API

MiniShop is a full-stack-ready backend built with **Java**, **Spring Boot**, **Hibernate**, and **Spring Data JPA** that supports core e-commerce features including:

- ✅ Product management (admin)
- 🛒 Cart creation and editing
- 💳 Checkout integration with **Stripe**
- 🔐 Role-based user functionality

---

## 🚀 Technologies Used

- Java 17+
- Spring Boot
- Hibernate & Spring Data JPA
- Stripe Java SDK
- MySQL
- Maven
- Lombok

---

## 📦 Features

### 🛍️ Product Management (Admin)
- Create, update, delete products
- Only accessible by users with `ADMIN` role

### 🛒 Cart
- Add products to cart
- Remove items or update quantity
- Store cart per user (session-based or user-based)

### 💳 Stripe Checkout
- Initiate checkout with Stripe Checkout Session API
- Automatically calculates total from cart
- Returns secure Stripe-hosted payment link
- Supports webhook to confirm payment and finalize order
