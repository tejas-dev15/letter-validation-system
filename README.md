# AI-Assisted Letter Validation & Automated Response Generation System

An AI-powered **document validation and automated response generation system** built using Spring Boot. The system analyzes official letters, extracts structured information, validates their format and content, and generates appropriate client-facing responses.

The project combines **LLMs, OCR, PDF processing, PostgreSQL, and knowledge-base-driven validation** to automate the processing of geospatial and remote-sensing related requests.

---

## Architecture Overview

The system follows a sequential AI-assisted document validation pipeline.

### Document Processing Layer

Responsible for receiving and processing uploaded documents.

- PDF / PNG / JPEG document upload
- File validation
- Document metadata storage
- PDF text extraction
- OCR using Tesseract
- Extracted text persistence

### AI Validation Layer

Responsible for understanding and validating the letter.

- Structured information extraction using an LLM
- Format validation
- Content validation
- Knowledge-base-driven validation
- Missing information detection

### Response Generation Layer

Responsible for converting validation results into a client-facing response.

- Validation result analysis
- Missing information explanation
- Correction guidance
- Automated response generation

---

## Technology Stack

### Backend

- **Language:** Java 21
- **Framework:** Spring Boot 4.1.0
- **Web:** Spring MVC
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven

### AI / NLP

- **AI Framework:** Spring AI 2.0.0
- **LLM Runtime:** Ollama
- **LLM Model:** Qwen 2.5 3B

### OCR & Document Processing

- **OCR:** Tesseract OCR
- **Java OCR Integration:** Tess4J
- **PDF Processing:** Apache PDFBox

### Database

- **Database:** PostgreSQL

### Development & Testing

- IntelliJ IDEA
- Git / GitHub
- Postman

---

## Key Features

### Document Upload

- Upload PDF, PNG, and JPEG documents
- File type validation
- File size validation
- Document metadata storage
- Document processing status tracking

### OCR & Text Extraction

- Extract text from PDF documents
- OCR support for image-based documents
- Tesseract integration through Tess4J
- Apache PDFBox integration for PDF processing
- Store extracted text in PostgreSQL

### AI-Based Letter Extraction

The system uses a local LLM to convert unstructured letter text into structured information.

The extraction process identifies:

- Date
- Reference number
- Recipient
- Subject
- Salutation
- Sender
- Organization
- Department
- Request type
- Purpose
- Geographic area
- Time period
- Data requirements
- Project / activity
- Requested action

The extraction process is instructed to use only information explicitly present in the letter and avoid inventing missing details.

### Format Validation

Validates whether the letter contains the required structural elements.

The validation checks include:

- Date
- Recipient
- Subject
- Salutation
- Body
- Closing
- Sender

Optional information is not incorrectly treated as mandatory.

### Content Validation

Validates the actual request against the project's knowledge base.

Applicable requirements include:

- Request type
- Purpose
- Geographic area
- Time period
- Data specification
- Project / activity
- Requested action
- Applicable supporting information
- Request consistency

### Knowledge-Base-Driven Validation

Validation rules are maintained separately from the Java business logic.

The knowledge base acts as the source of truth for content validation, allowing validation rules to be updated independently.

### Automated Response Generation

The system generates a client-facing response based on the validation results.

For invalid requests, the generated response explains:

- Missing information
- Identified issues
- Required corrections

For valid requests, the response confirms that the request satisfies the applicable requirements and can proceed.

---

## System Workflow

```text
                         Client Letter
                              |
                              v
                    +-------------------+
                    |  Document Upload  |
                    +---------+---------+
                              |
                              v
                    +-------------------+
                    | OCR / PDF Text    |
                    | Extraction        |
                    +---------+---------+
                              |
                              v
                  +-------------------------+
                  | Letter Information       |
                  | Extraction               |
                  +-----------+-------------+
                              |
                    +---------+---------+
                    |                   |
                    v                   v
          +----------------+   +-------------------+
          | Format         |   | Content           |
          | Validation     |   | Validation        |
          +-------+--------+   +---------+---------+
                                        |
                                        v
                              +-------------------+
                              | Knowledge Base    |
                              +---------+---------+
                                        |
                                        v
                              +-------------------+
                              | Validation        |
                              | Results           |
                              +---------+---------+
                                        |
                                        v
                              +-------------------+
                              | Response          |
                              | Generation        |
                              +---------+---------+
                                        |
                                        v
                                  Client Response
AI Architecture

The application uses Spring AI's ChatClient to communicate with a locally running Ollama instance.

+-------------------------+
|     Spring Boot App     |
+------------+------------+
             |
             v
+-------------------------+
|    Spring AI ChatClient |
+------------+------------+
             |
             v
+-------------------------+
|         Ollama          |
|    localhost:11434      |
+------------+------------+
             |
             v
+-------------------------+
|       Qwen 2.5 3B       |
+-------------------------+

Using Ollama allows the application to perform LLM inference locally without relying on an external LLM API during development and testing.

Core Modules
Document Management

Handles:

Document upload
File validation
Metadata persistence
Processing status management
OCR Service

Handles:

PDF text extraction
Image OCR
Extracted text persistence
Letter Extraction Service

Converts unstructured letter text into structured LetterDetails.

Format Validation Service

Determines whether the letter follows the required document structure.

Content Validation Service

Checks extracted request information against applicable knowledge-base rules.

Knowledge Base Service

Loads the project's validation knowledge base.

Knowledge Base Retriever

Provides knowledge-base content to the content validation process.

Response Generation Service

Converts validation results into a clear client-facing response.

Letter Validation Service

Coordinates the complete validation workflow.

Knowledge Base

The project contains a synthetic development knowledge base for validating geospatial and remote-sensing related requests.

The knowledge base contains rules related to areas such as:

Request type
Purpose
Geographic area
Time period
Data requirements
Output requirements
Project/activity information
Supporting information
Requested action
Request consistency

The validation system is designed to apply only relevant rules and avoid treating optional information as mandatory.

Note: The included knowledge base contains synthetic development data and does not contain confidential MRSAC information.

Project Structure
src/
└── main/
    ├── java/
    │   └── com/mrsac/lettervalidation/
    │       │
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
    │       └── service/
    │           ├── DocumentService
    │           ├── OcrService
    │           ├── LetterExtractionService
    │           ├── LetterValidationService
    │           ├── KnowledgeBaseService
    │           ├── KnowledgeBaseRetriever
    │           ├── ContentValidationService
    │           └── ResponseGenerationService
    │
    └── resources/
        ├── knowledge-base/
        │   └── mrsac_dummy_knowledge_base.txt
        │
        └── application.properties
API Modules

The application exposes APIs for different stages of the document processing pipeline.

Document APIs
POST /documents/upload

Handles document upload and document processing.

OCR APIs
POST /ocr/{documentId}

Processes an uploaded document and extracts its text.

AI Test API
GET /api/ai/test

Used to verify communication between Spring AI and the local Ollama model.

Letter Extraction APIs

Handles extraction of structured information from letter text.

Validation APIs

Handles:

Format validation
Content validation
Complete letter validation
Response Generation

Generates the final client-facing response based on validation results.

Example Processing Flow
1. Client uploads a letter
              |
              v
2. Document metadata is stored
              |
              v
3. OCR extracts the letter text
              |
              v
4. LLM extracts structured information
              |
              v
5. Format validation is performed
              |
              v
6. Content is validated against the knowledge base
              |
              v
7. Missing information and issues are identified
              |
              v
8. Response generator creates client-facing response
Database

The application uses PostgreSQL for persistent document storage.

The documents table stores information such as:

Document ID
Original file name
File type
File path
Extracted text
Processing status
Upload timestamp

The extracted letter text is stored as PostgreSQL TEXT.

Validation Principles

The AI validation pipeline follows several important principles:

Do not invent missing information
Do not assume information that is not explicitly present
Apply only relevant validation rules
Do not mark optional information as missing
Clearly identify missing required information
Explain inconsistencies
Use the knowledge base as the validation source
Keep validation decisions separate from client-facing response generation
Error Handling

The application includes centralized exception handling for consistent API responses.

The exception layer provides:

Structured error responses
Invalid document handling
Validation error handling
Centralized exception processing
Configuration

The application requires:

Java 21
PostgreSQL
Ollama
Qwen 2.5 3B
Tesseract OCR

Example Ollama configuration:

spring.ai.model.chat=ollama

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=qwen2.5:3b

Database credentials should be provided through local configuration or environment-specific configuration.

Sensitive credentials should not be committed to the repository.

Getting Started
Prerequisites

Install the following:

Java 21
Maven
PostgreSQL
Ollama
Tesseract OCR
Clone the Repository
git clone <repository-url>
cd letter-validation-system
Start Ollama

Make sure Ollama is running and the required model is available:

ollama run qwen2.5:3b

The application communicates with Ollama through:

http://localhost:11434
Configure PostgreSQL

Create the required PostgreSQL database and configure the database credentials in:

src/main/resources/application.properties
Build the Project
mvn clean install

Or using the Maven wrapper:

./mvnw clean install
Run the Application
mvn spring-boot:run
Testing

The APIs can be tested using Postman or another API client.

The system was tested using synthetic letters representing different validation scenarios, including:

Properly formatted and complete requests
Missing mandatory information
Incomplete data specifications
Vague requested actions
Missing time periods
Incomplete project descriptions

The complete pipeline was tested with the local Ollama-based LLM implementation.

Project Status
Completed
Document upload
PostgreSQL persistence
OCR integration
PDF text extraction
LLM-based letter extraction
Format validation
Knowledge-base-driven content validation
Automated response generation
Local Ollama integration
End-to-end validation workflow

The complete letter validation and automated response generation workflow is functional as the stable internship implementation.

Future Improvements

The stable internship implementation intentionally focuses on the core validation workflow.

Future development can explore:

Redis-based caching
Apache Kafka-based asynchronous processing
Event-driven document processing
Vector database integration
Retrieval-Augmented Generation (RAG)
Improved semantic knowledge-base retrieval
Asynchronous processing
Authentication and authorization
Monitoring and observability
Scalable production deployment

These advanced experiments can be developed separately without modifying the stable internship implementation.

Architecture Evolution
Current System
                    Spring Boot
                         |
        +----------------+----------------+
        |                |                |
        v                v                v
   PostgreSQL          OCR          Ollama / LLM
        |                                 |
        +---------------+-----------------+
                        |
                        v
                Validation Pipeline
Future LetterFlow Architecture
                         Spring Boot
                              |
          +-------------------+-------------------+
          |                   |                   |
          v                   v                   v
       Redis               Kafka             PostgreSQL
          |                   |                   |
          |                   v                   |
          |            Event Processing           |
          |                   |                   |
          +-------------------+-------------------+
                              |
                              v
                       Vector Database
                              |
                              v
                         Ollama / LLM

The advanced architecture will be explored separately so that the stable internship implementation remains unchanged.

Key Learning Outcomes

This project provided practical experience in:

Spring Boot backend development
REST API design
Spring Data JPA
PostgreSQL
OCR integration
PDF processing
LLM integration
Spring AI
Local LLM deployment using Ollama
Prompt engineering
Structured information extraction
AI-assisted validation
Knowledge-base-driven validation
Exception handling
Backend architecture
AI-assisted document processing
Important Notes
PostgreSQL must be running before starting the application.
Ollama must be installed and running locally.
The qwen2.5:3b model must be available in Ollama.
Tesseract OCR must be correctly configured.
The included knowledge base uses synthetic development data.
No confidential MRSAC data is included in this repository.
Sensitive credentials should be stored using environment variables or local configuration.
Redis, Kafka, and vector database integration are not part of the current stable internship implementation.
Disclaimer

This project was developed as a research internship project for educational and research purposes.

The knowledge base included in this repository contains synthetic development data and should not be considered an official representation of MRSAC policies, procedures, requirements, or confidential information.

Authors
Tejas

Computer Science Engineering Student

Interested in backend engineering, AI-assisted systems, distributed systems, and real-world software architecture.

Vansh Nagpure

Project Contributor

Final Thoughts

This project demonstrates how traditional backend engineering can be combined with OCR, LLMs, and knowledge-base-driven validation to automate document processing workflows.

It bridges:

Backend Engineering
        +
Artificial Intelligence
        +
OCR / Document Processing
        +
Database Systems
        +
Knowledge-Based Validation

The project provides a foundation for evolving a synchronous backend into a more scalable, event-driven, and AI-powered system.
