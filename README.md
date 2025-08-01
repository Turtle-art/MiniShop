# 🛍️ MiniShop – E-Commerce API

MiniShop is a full-stack-ready backend built with **Java**, **Spring Boot**, **Hibernate**, and **Spring Data JPA** that supports core e-commerce features including:

- Product management (admin)
-  Cart creation and editing
-  Checkout integration with **Stripe**
-  Role-based user functionality

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
- Create, update, delete products
- Only accessible by users with `ADMIN` role

### Cart
- Add products to cart
- Remove items or update quantity
- Store cart per user (session-based or user-based)

### Stripe Checkout
- Initiate checkout with Stripe Checkout Session API
- Automatically calculates total from cart
- Returns secure Stripe-hosted payment link
- Supports webhook to confirm payment and finalize order

  Here is a complete `README.md` section formatted properly, focusing on what a webhook is, both simply and formally, and how it’s used in your project:

---

# Understanding Webhooks in This Project

## What is a Webhook? (Simple Explanation)

A webhook is like asking Stripe:
"When something happens, please knock on my door and tell me about it."

Imagine you order a pizza. Instead of calling the delivery person every few minutes to ask where they are, you just wait at home. When the pizza arrives, the delivery person rings your doorbell to let you know it's here.

A webhook works the same way. Instead of your application repeatedly asking Stripe if a payment is done, Stripe will send a message (an HTTP request) to your application as soon as the payment is complete. That message contains information about what happened (e.g. “the payment was successful”).

## Official Definition

A **webhook** is an HTTP callback: a way for one system to notify another when an event occurs. Webhooks are typically triggered by some action and send a payload (data) to a specified URL using an HTTP POST request.

In Stripe’s case, webhooks allow Stripe to notify your backend server when important events occur, such as:

* A payment is successfully completed
* A payment fails
* A subscription is cancelled

Webhooks are an essential tool in event-driven applications where real-time updates are needed without constant polling.

## How Webhooks Are Used in This Project

This Spring Boot eCommerce backend integrates Stripe for payment processing. Here's how webhooks fit into the flow:

1. A customer adds products to their cart and proceeds to checkout.
2. The backend creates a **Stripe Checkout Session**.
3. The customer is redirected to Stripe’s secure hosted payment page.
4. After the payment is completed, Stripe sends a **webhook POST request** to your server at the endpoint:

```
POST /api/stripe/webhook
```

5. This request includes an event, such as `checkout.session.completed`, in JSON format.
6. The backend processes the event by:

   * Verifying the Stripe signature to confirm it’s a valid request.
   * Reading the metadata (such as order ID) to update the order in the database.
   * Marking the order as `PAID` or performing any other necessary logic.

This webhook ensures the backend finalizes and saves the order only after the payment is successfully processed, even if the user closes their browser or loses internet connection before returning to the site.

## Why Use Webhooks Instead of Redirects

Using webhooks instead of relying on frontend redirects has multiple benefits:

* It’s more reliable: payment confirmation doesn’t depend on the customer returning to your site.
* It’s secure: Stripe signs each webhook so your server can verify its authenticity.
* It allows backend-driven order processing that doesn’t rely on user behavior after checkout.
