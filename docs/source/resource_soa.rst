Resource SOA
============

The resource storing service is presented here in format of SOA application. The framework that lays under the neath of
the service is Java VertX. This framework integrates the concepts of reactivity and decoupling in scope of verticles.
Each verticle represents the working unit of the application: Here are the elements:
- API: serves multimedia resources via REST API and streaming channel.
- MQ: X-service-interaction module that allows to handle more flexibility and fault-tolerance.
- BLOB storage: the module that allows to store the resources in cloud BLOBs: here is using Cloud Storage.

The communications between modules are performing with usage of event bus. This is a big part that allows to build the
reactive-approach implementations. The reactivity ensures by implying the event-driven architecture based on the Promise
and Future models resulted by async-calls.

The pros of the approach is more flexibility in arranging modules and computing resources that is needed for the service
delivery. This leads to easier extensibility of the system: while implementing new functional requirements we can group
them into some working units that corresponds to software component models(API, DB), async processing: the responses for
the requests will process quickly(literally real-time solutions requires this), resource-efficiency: quicker processing
decreases the resource-consumptions etc.

The drawbacks of the approach is in its complication in set-up, testing and handling. From the one side we have
decomposition that allows to elaborate the service functionality to fit its work in best way. On the contrary, we got
more complex implementation: harder to write tests, it requires more time to run, harder to debug the event-bus. Also
one moment to note - is the asynchronous implementation approach. As the VertX works with the approach of multi
event-loops and processes that attached to them - we also need to ensure its non-blocking run. The case, when we block
the thread leads to system delayed responses and degraded user experience. The solution for tracking such narrow
cases could be painful and complex, as we interact with only the API of the approaches implementation.
