DDD and MDA
=========================
Domain-Driven Architecture and Model-Driven design is a concept of decomposition of the systems based on some
abstractions.

Domain-Driven Architecture of the Hale-Bopp Application: its domain, bounded contexts and components are described in the
main article of the documentation.

This approach of building microservice architecture in the current application is redundant as the functionality is
quite low. But as the system will grow, the necessity of the approach will be increased.

The main concepts of the MDA in current scope are:

Compute independent model
---------------------

Business requirements focus:

1. User uploads the music
2. User listens to music in streaming format

Platform independent model
------------------------

The platform independent system description: The main entities are described on the main page of the documentation.
UML diagrams is redundant in this case, but each microservice contains the separate package `domain` what contains
the core models that lays under the service.

Platform Specific model
--------------------
As the representation of entities is created, we choose the technology to implement the processes. In this case they are:
- Java Spring: server creation
- Postgres: Relational DB
- Google Cloud Storage(Amazon S3 - legacy implementation) - cloud-based BLOB

From the one side - DDD approach is more business-follow rules but on the other side the MDA - is more technical.
