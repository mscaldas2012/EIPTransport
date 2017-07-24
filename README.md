This project implements the EIP Transport Services.

Simple method with endpoint to receive a valid Message to be processed later by EIP Services.

This service is payload agnostic, meaning, it can receive anythign through the payload.content variable,  
as long as there's content present.

Content should be encoded to avoid problems with special characters as line feeds. Base64 is the supported
encoding for the current version. 