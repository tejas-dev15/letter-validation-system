# 🤖 AI-Assisted Letter Validation & Automated Response Generation System

> An AI-powered backend system for validating client request letters, identifying missing or incomplete information, and automatically generating appropriate responses.

---

## 📌 Overview

The **AI-Assisted Letter Validation & Automated Response Generation System** is a Spring Boot–based backend application developed to automate the validation of incoming client letters requesting geospatial and remote-sensing related data or services.

The system combines:

- ☕ **Java & Spring Boot**
- 🤖 **Large Language Models (LLMs)**
- 👁️ **OCR**
- 🗄️ **PostgreSQL**
- 📚 **Knowledge Base–Driven Validation**
- 🧠 **Ollama + Qwen 2.5**
- 📄 **PDF and image document processing**

Instead of manually checking every incoming letter, the system extracts relevant information, validates the letter's format and content, and generates a clear response explaining whether corrections are required.

---

## 🎯 Project Objective

The primary objective is to build an intelligent system capable of answering:

> **"Does this request letter contain all the information required to process the requested service?"**

The system performs this through multiple stages:

1. 📤 Upload the document
2. 🔍 Extract text using OCR
3. 🧾 Extract structured information from the letter
4. 📋 Validate the letter's format
5. 📚 Validate the requested content against the knowledge base
6. ✍️ Generate an appropriate response

---

## 🏗️ System Architecture

```text
                    ┌──────────────────────┐
                    │      Client/User     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Spring Boot API   │
                    └──────────┬───────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
       ┌────────────┐   ┌──────────────┐  ┌──────────────┐
       │   Upload   │   │     OCR      │  │  Validation  │
       │   Module   │   │    Module    │  │    Module    │
       └────────────┘   └──────────────┘  └──────┬───────┘
                                                   │
                                                   ▼
                                         ┌──────────────────┐
                                         │      Ollama      │
                                         │   Qwen 2.5:3B    │
                                         └────────┬─────────┘
                                                  │
                         ┌────────────────────────┼────────────────────┐
                         │                        │                    │
                         ▼                        ▼                    ▼
                  ┌──────────────┐      ┌──────────────────┐   ┌──────────────┐
                  │   Letter     │      │ Knowledge Base   │   │   Response   │
                  │  Extraction  │      │    Validation    │   │  Generation  │
                  └──────────────┘      └──────────────────┘   └──────────────┘
                                                  │
                                                  ▼
                                         ┌──────────────────┐
                                         │    PostgreSQL    │
                                         └──────────────────┘
🧠 AI Architecture

The application uses Spring AI's ChatClient to communicate with a locally running LLM through Ollama.

Spring Boot Application
          │
          ▼
    Spring AI ChatClient
          │
          ▼
      Ollama API
          │
          ▼
     Qwen 2.5:3B
          │
          ▼
   Structured / Text Output
Why Ollama? 🦙

The application uses a local LLM instead of relying on an external hosted API.

Benefits include:

🔒 Local AI processing
💰 No per-request API cost
🌐 No dependency on external AI APIs during runtime
🧪 Easy experimentation with different local models
🛠️ Suitable for development and experimentation
🧩 Core Modules
1. 📤 Document Upload Module

Responsible for receiving client documents through the REST API.

Supported formats include:

PDF
PNG
JPEG

The module:

Validates the uploaded file
Checks file size
Stores document metadata
Saves the document
Creates a database record
2. 👁️ OCR Module

The OCR module extracts text from uploaded documents.

Technologies used:

Tesseract
Tess4J
Apache PDFBox

For supported documents, the extracted text is stored in PostgreSQL and becomes the input for the validation pipeline.

3. 🧾 Letter Extraction Module

The extracted letter text is passed to the LLM to identify structured information.

The system extracts fields such as:

Date
Reference Number
Recipient
Subject
Salutation
Sender
Organization
Department
Body
Request Type
Purpose
Geographic Area
Time Period
Data Requirements
Project Activity
Requested Action

The model is explicitly instructed not to invent information that is not present in the letter.

4. 📋 Format Validation Module

This module checks whether the letter contains the expected structural elements.

The validator checks elements such as:

Date
Recipient
Subject
Salutation
Body
Closing
Sender

The system focuses on the semantic presence of these sections, rather than requiring an exact physical document layout.

Reference numbers are treated as optional.

5. 📚 Knowledge Base–Driven Content Validation

The content validator determines whether the actual request contains the information required to process it.

The validation rules are maintained in a knowledge base rather than being hard-coded directly into Java business logic.

The knowledge base contains rules related to areas such as:

Request type
Purpose
Geographic area
Time period
Data requirements
Output format
Project/activity information
Supporting documents
Requested action
Consistency between different parts of the request

The system applies only the rules relevant to the particular request.

6. ✍️ Response Generation Module

After format and content validation, the system generates a client-facing response.

For an invalid request, the response explains:

❌ What information is missing
⚠️ What issues were identified
📝 What needs to be corrected

For a valid request, the system confirms that the request contains the required information and can proceed.

The generated response is intentionally kept concise and focused on the validation outcome.

📚 Knowledge Base

The current implementation uses a synthetic development knowledge base created specifically for this project.

⚠️ The knowledge base does not contain confidential MRSAC information.

The current implementation loads the knowledge base and provides it to the LLM during content validation.

Example validation principles
Required information should be explicitly present.

Do not assume information that is not provided.

Optional information should not be treated as mandatory.

Time period is required when the requested data depends on a specific
time period.

The requested action should be clear.

Contradictory information should be reported.

Only applicable validation rules should be applied.

This approach keeps the validation logic separate from the Java application code and allows the knowledge base to act as the source of validation rules.

🔄 Complete System Workflow
                📄 Client Letter
                       │
                       ▼
               📤 Document Upload
                       │
                       ▼
                💾 Store Document
                       │
                       ▼
                    👁️ OCR
                       │
                       ▼
              📝 Extracted Text
                       │
                       ▼
             🧠 LLM Information
                 Extraction
                       │
                       ▼
              ┌─────────────────┐
              │ Letter Details  │
              └────────┬────────┘
                       │
            ┌──────────┴──────────┐
            ▼                     ▼
     📋 Format Validation   📚 Content Validation
            │                     │
            └──────────┬──────────┘
                       ▼
               ✍️ Response
                Generation
                       │
                       ▼
              📩 Final Response
🛠️ Technology Stack
Technology	Purpose
☕ Java 21	Primary programming language
🌱 Spring Boot	Backend framework
🧠 Spring AI	LLM integration
🦙 Ollama	Local LLM runtime
🤖 Qwen 2.5:3B	Local language model
🗄️ PostgreSQL	Database
🧩 Spring Data JPA	Database interaction
👁️ Tesseract / Tess4J	OCR
📄 Apache PDFBox	PDF text processing
📦 Maven	Dependency management
🧪 Postman	API testing
💻 IntelliJ IDEA	Development environment
📁 Project Structure
src/
└── main/
    ├── java/
    │   └── com/mrsac/lettervalidation/
    │       ├── config/
    │       │
    │       ├── controller/
    │       │   ├── DocumentController
    │       │   ├── OcrController
    │       │   ├── OpenAiController
    │       │   ├── LetterExtractionController
    │       │   └── ResponseController
    │       │
    │       ├── dto/
    │       │   ├── DocumentUploadResponse
    │       │   ├── LetterDetails
    │       │   ├── LetterStructure
    │       │   ├── LetterFormatValidationResult
    │       │   ├── ContentValidationResult
    │       │   └── GeneratedResponse
    │       │
    │       ├── entity/
    │       │   ├── Document
    │       │   └── DocumentStatus
    │       │
    │       ├── exception/
    │       │   ├── ErrorResponse
    │       │   ├── GlobalExceptionHandler
    │       │   └── InvalidDocumentException
    │       │
    │       ├── repository/
    │       │   └── DocumentRepository
    │       │
    │       ├── service/
    │       │   ├── DocumentService
    │       │   ├── OcrService
    │       │   ├── LetterExtractionService
    │       │   ├── LetterValidationService
    │       │   ├── KnowledgeBaseService
    │       │   ├── KnowledgeBaseRetriever
    │       │   ├── ContentValidationService
    │       │   └── ResponseGenerationService
    │       │
    │       └── LetterValidationSystemApplication
    │
    └── resources/
        ├── knowledge-base/
        │   └── mrsac_dummy_knowledge_base.txt
        │
        └── application.properties

ℹ️ OpenAiController is a legacy class name from the earlier OpenRouter/OpenAI-compatible integration. The current AI runtime is Ollama + Qwen 2.5:3B.

🔌 API Modules

The application exposes REST APIs for different stages of the processing pipeline.

📄 Document APIs

Responsible for:

Uploading documents
Managing document processing
👁️ OCR APIs

Responsible for:

Triggering OCR
Extracting text from documents
🧠 Letter Extraction APIs

Responsible for:

Extracting structured letter information
Performing format validation
✍️ Response APIs

Responsible for:

Running the validation pipeline
Generating the final response
🧪 Example Processing
Input
Subject: Request for Satellite Imagery for Wardha District

We are undertaking a project related to satellite imagery and request
MRSAC to provide the required satellite data for our work.

The requested satellite imagery is intended for our project activities.
We kindly request you to provide the necessary data and support.
Possible Validation
CONTENT VALID: false

MISSING INFORMATION:
- Specific time period for the satellite imagery

ISSUES:
- Purpose of the request is insufficiently specific
- Data requirements are not sufficiently specified
- Requested action is vague
Generated Response

The response-generation module converts the validation result into a client-facing explanation describing the corrections required before the request can be processed.

🗄️ Database

The application uses PostgreSQL for persistent document information.

The documents table stores information including:

Document ID
Original file name
File type
File path
Extracted text
Processing status
Upload timestamp
Document Status
UPLOADED
    ↓
PROCESSING
    ↓
OCR_COMPLETED
    ↓
VALIDATED
    ↓
COMPLETED

If an error occurs during processing:

PROCESSING
    ↓
FAILED
🛡️ Validation Principles

The validation system follows several important principles:

🚫 No Hallucination

The LLM is instructed not to invent information that does not exist in the letter.

🔎 Explicit Information Only

Information must be explicitly available in the extracted letter content.

📚 Knowledge Base as Source of Rules

Validation requirements come from the knowledge base rather than being manually hard-coded into the validation service.

🎯 Applicable Rules Only

Optional or irrelevant requirements should not cause a request to fail.

⚠️ Clear Error Reporting

Missing information and inconsistencies are reported explicitly so the requester knows what needs to be corrected.

⚙️ Configuration

The application currently uses Ollama locally.

Example configuration:

spring.ai.model.chat=ollama

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=qwen2.5:3b

The Ollama model can be changed depending on the available hardware and desired performance.

🚀 Getting Started
1️⃣ Clone the repository
git clone <repository-url>
2️⃣ Open the project

Open the project using IntelliJ IDEA or another Java IDE.

3️⃣ Verify Java

Make sure Java 21 is installed.

java -version
4️⃣ Verify Ollama

Install and start Ollama, then verify the model is available.

ollama list

The required model:

qwen2.5:3b
5️⃣ Configure PostgreSQL

Create the required PostgreSQL database and configure the database connection in application.properties.

6️⃣ Start the application

Using Maven:

./mvnw spring-boot:run

On Windows:

mvnw.cmd spring-boot:run
7️⃣ Test the APIs

Use Postman or another REST client to test the application endpoints.

🧪 Testing

The project was tested across multiple stages of the pipeline, including:

📤 Document upload
👁️ OCR extraction
💾 Database persistence
🧾 Letter information extraction
📋 Format validation
📚 Content validation
✍️ Automated response generation
🦙 Local Ollama inference

Different letter formats and content scenarios were used to verify that the validation pipeline could distinguish between complete and incomplete requests.

🏆 Project Status
✅ Completed
 Document upload
 PostgreSQL persistence
 OCR integration
 PDF processing
 Letter information extraction
 Format validation
 Knowledge Base–Driven Content Validation
 Automated response generation
 Ollama integration
 Qwen 2.5:3B integration
 End-to-end validation pipeline
 API testing
 Error handling
🎉 Current State

The internship version of the project is complete and stable.

The current implementation is intentionally being kept as the final internship version.

🔮 Future Improvements

The current internship implementation provides a foundation for a more advanced architecture.

Possible future improvements include:

🧠 Real RAG

Replace the current whole-knowledge-base prompt approach with a proper retrieval pipeline.

Potential components:

Vector database
Embeddings
Semantic search
Chunking
Metadata filtering
Context-aware retrieval
⚡ Redis

Redis can be introduced for:

Caching
Temporary processing state
Frequently accessed data
Performance optimization
📨 Apache Kafka

Kafka can be used to introduce asynchronous event-driven processing.

For example:

Document Uploaded
       ↓
Kafka Event
       ↓
OCR Service
       ↓
Validation Service
       ↓
Response Generation

This would allow different stages of the pipeline to operate asynchronously.

🔄 Asynchronous Processing

Long-running OCR and AI operations could be processed asynchronously rather than keeping a request waiting for the entire pipeline.

📊 Monitoring & Observability

Future versions could introduce:

Structured logging
Metrics
Distributed tracing
Health monitoring
Processing-time measurement
🧪 Architecture Evolution

The current internship project focuses on getting the complete validation workflow working reliably.

A future experimental version, LetterFlow, can be used to explore more advanced backend engineering concepts without changing the completed internship implementation.

                 CURRENT INTERNSHIP VERSION
                            │
                            ▼
                 ┌─────────────────────┐
                 │    Spring Boot      │
                 │      + Ollama       │
                 │      + PostgreSQL   │
                 └─────────────────────┘
                            │
                            ▼
                 Knowledge Base
                 Driven Validation


                            │
                            │ Future Evolution
                            ▼


                     LETTERFLOW 🚀
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          Redis          Kafka         Vector DB
             │              │              │
             └──────────────┼──────────────┘
                            ▼
                       Advanced RAG
                            │
                            ▼
                 Async Event-Driven
                      Architecture
💡 Key Learning Outcomes

This project provided practical experience with:

☕ Advanced Java backend development
🌱 Spring Boot architecture
🗄️ PostgreSQL and JPA
📄 Document processing
👁️ OCR integration
🤖 LLM integration
🧠 Spring AI
🦙 Local LLM deployment with Ollama
📚 Knowledge Base–Driven validation
🔌 REST API development
🧩 DTO-based architecture
⚠️ Exception handling
🧪 API testing
🏗️ Designing multi-stage backend workflows
🔐 Important Notes
The knowledge base used in this project is synthetic development data.
No confidential MRSAC information is included in the repository.
The current implementation does not use a vector database.
The current implementation does not implement production-grade RAG.
Advanced RAG, Redis, Kafka, and event-driven processing are planned as future experimentation rather than part of the frozen internship implementation.
The local LLM is used through Ollama.
📜 Disclaimer

This project was developed as an internship/research project for demonstrating automated document validation and AI-assisted response generation.

The current knowledge base contains synthetic information for development and testing purposes and should not be considered an official representation of organizational policies or procedures.

👨‍💻 Authors

Tejas	Dange
Vansh Nagpure	

Final Thoughts

This project combines traditional backend engineering with modern AI capabilities to create an automated document-processing workflow.

From:

📄 Raw Document

to:

👁️ OCR

to:

🧠 AI Extraction

to:

📋 Validation

to:

📚 Knowledge Base Reasoning

to:

✍️ Automated Response

the system demonstrates how an AI-assisted backend can be integrated into a practical document-processing pipeline.

🚀 Built with Java, Spring Boot, Spring AI, PostgreSQL, Tesseract, Ollama & Qwen 2.5.
