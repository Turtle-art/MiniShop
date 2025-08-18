# 🛍️ MiniShop – E-Commerce API

MiniShop is a full-stack-ready backend built with **Java**, **Spring Boot**, **Hibernate**, and **Spring Data JPA** that supports core e-commerce features including:

- Product management (admin)
- Cart creation and editing
- Checkout integration with **Stripe**
- Role-based user functionality

---

## Technologies Used

- Java 17+
- Spring Boot
- Hibernate & Spring Data JPA
- Stripe Java SDK
- MySQL
- Maven
- Lombok

---

## Features

### Product Management (Admin)
- Create, update, and delete products  
- Only accessible by users with the `ADMIN` role

### Cart
- Add products to cart  
- Remove items or update quantities  
- Store cart per user (session-based or user-based)

### Stripe Checkout
- Initiate checkout using Stripe’s Checkout Session API  
- Automatically calculates the total from the cart  
- Returns a secure, Stripe-hosted payment link  
- Supports webhooks to confirm payment and finalize orders

### Swagger API Documentation
- Fully documented endpoints for testing and integration

### Sl4j Logging
- Added logs in services using sl4j

---

## How Ordering Works

### 1. Adding Products to the Cart  
A customer selects products and specifies quantities. These items are stored in their personal cart, linked to their user account.

---

### 2. Checkout Process  
When a customer proceeds to checkout:
- The system retrieves all cart items for that customer.  
- An order is created in the database with a **pending status** and a unique tracking ID.  
- The order’s total is calculated based on the products and quantities in the cart.  
- A Stripe Checkout Session is created, containing the order details and payment amount.  
- The customer is redirected to Stripe’s secure payment page.  

---

### 3. Payment  
The customer completes payment on Stripe’s hosted page. This step ensures that sensitive card information never touches your backend.

---

### 4. Payment Confirmation  
Once payment is completed, Stripe sends a notification (via a webhook) to the backend. The backend:
- Verifies the authenticity of the notification.  
- Matches it to the corresponding order using metadata from the payment session.  
- Updates the order status to **PAID** in the database.  

This process works even if the customer closes their browser before returning to the site.

---

### 5. Order Tracking  
Every order has a unique tracking ID generated at creation. This ID can be used to monitor the order’s status or track delivery progress.

---

## Why This Flow Works Well
- **Backend-driven**: The payment confirmation happens entirely on the server side, ensuring accuracy.  
- **Secure**: Payment details are handled by Stripe, and all webhook events are verified before processing.  
- **Reliable**: Orders are finalized even if the user disconnects or fails to return after payment.  

---

## Understanding Webhooks

### What is a Webhook? (Simple Explanation)  
A webhook is like asking Stripe:  
*"When something happens, please knock on my door and tell me about it."*  

It’s similar to ordering a pizza — instead of calling the delivery driver every five minutes to ask where they are, you wait at home. When the pizza arrives, they ring your doorbell. In the same way, Stripe notifies your application automatically when a payment is complete.

---

### Official Definition  
A **webhook** is an HTTP callback — a way for one system to notify another when an event occurs. The sending system triggers the webhook and sends data (the “payload”) to a specified URL via an HTTP POST request.

---

### How Webhooks Are Used Here  
In MiniShop, Stripe webhooks are essential for confirming payments:  
1. After a customer checks out and pays, Stripe triggers a webhook event.  
2. This event contains details about the payment, including which order it belongs to.  
3. The backend verifies the event’s authenticity and updates the order status in the database.  

By using webhooks instead of relying only on browser redirects, the payment process is more reliable, secure, and independent of customer behavior after checkout.
