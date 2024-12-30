.. hale-bopp documentation master file, created by
   sphinx-quickstart on Tue Dec 24 18:27:33 2024.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Hale-Bopp Documentation
=======================

Hale-Bopp is the service for streaming music. This is demonstration variant of the microservice architecture
written in Java. The goal of the service is to build sample components and arrange it in ways.
The architecture will be described in format of C4 specification.

This documentation will focus only on implementation of functional requirements of the system.

The domain of the system includes such aspects:
- Users can upload music to the streaming service
- Users can identify and work with song data: name, album, year etc

The domain language of the system:
- Track: song that is represented in application
- Artist
- Album
- Resource: the resource under the song that is used for streaming and serving the song

Core Subdomains Identification and Bounded Contexts
=====

The music streaming application has several subdomains:
1. Catalog Storage Context
2. Resource and Streaming Context
3. Music Processing Context


Catalog Storage Context
------


.. uml:: context.puml

The diagram above shows the context-level diagram of the architecture. Here we have actor who is using the application.
The user interacts only with Hale-Bopp music streaming service. In the same time, the service is communicating through
the internet with Google Cloud, where all resources are stored.

Component-Level Diagram
--------

The following diagram shows the component-level of the architecture of the music application

.. uml:: container.puml

Here we have three pivotal services. The logic under the diagram is following:
1. User uploads the music to the resource processing service: on this step we should define if the music is still present in the library.
2. Parsing music metadata: extracting the name, album and other data.
3. If the music is still present - then we should do nothing.
4. If the music is not present in the common library - we shall update the music data and upalod resource to the Cloud Storage
5. Pushing the resource to the `resource-service`

After the pre-check is done and in case of uniqueness of the music - uploading the resource under the music, we can service the resource to the user.

.. include:: decomposition_description.rst
.. include:: resource_soa.rst
.. include:: service_discovery.rst