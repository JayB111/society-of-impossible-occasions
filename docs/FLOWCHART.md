# Application Flowchart

## The Society of Impossible Occasions

```mermaid
flowchart TD
    A([Start application]) --> B[Connect to database]
    B --> C[Display main menu]
    C --> D{Select option}

    D --> E[Manage events]
    D --> F[Manage participants]
    D --> G[Manage invitations]
    D --> H[Display information]
    D --> I([Exit application])

    E --> C
    F --> C
    G --> C
    H --> C
```
