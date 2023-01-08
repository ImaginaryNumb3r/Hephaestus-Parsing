# Element Naming
 - **Terminal:** An atomic element with a fixed length. It is either parsed as a whole or not at all.
 - **Token:** An atomic element with arbitrary length. It can be empty, parsed and unparsed.
 - **Node:** A node is an element which contains one or more other elements. It represents a pattern and is not an elemental structure.
   - For example, a sequence of nodes which must be parsed in the correct oder is `SequenceNode` 
 - **Consumer:** A element of arbitrary length. Accepts characters until its predicate is violated.
