Simple Validator
================

This example demonstrates how to implement a simple protocol validator.

This validator simply scans a protocol model to determine whether it contains an
interaction with a message type of 'Order'. If so, it reports an error associated
with the interaction.

The SimpleValidator class implements the ProtocolValidator interface directly,
and creates its own model visitor implementation to scan the model.

Alternatively a specialisation of org.scribble.protocol.validation.ProtocolComponentValidator
could be used, with a set of org.scribble.protocol.validation.ProtocolComponentValidatorRule
implementations registered to validate specific model object types.

To build the example validator you will need to install Maven, and then invoke:

	mvn clean install

This will create a jar file in the target folder. This jar should be copied into the
top level 'bundle' folder (or Eclipse plugin folder).

Once installed, run the validation on a protocol model that uses the 'Order' message
type to see the error.
