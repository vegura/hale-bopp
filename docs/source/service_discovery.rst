Service Discovery
================

This approach uses already implemented service discovery written in Java by Netflix - Eureka. The pivotal variant of
the system is client-side discovery. This means that each service to grab the connection to some another service
needs to request it from internal third-party middleware, hence before usage of the service it is needed to be
registered. Due to Eureka, this works seamlessly.