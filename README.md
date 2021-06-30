# Hephaestus-Parsing

**Warning:** This project is a work in progress which started out as a proof of concept. The quality of tests and documentation is steadily increasing but the WIP nature means that the majority of the codebase of the code is subject to change.

The idea behind this project was a token-based API that allows you to define a strongly typed syntax tree where every node in the tree is well-defined by a class.
The ultimate goal is an extensible API which allows to model Context Free Grammars.

This project originated in [Hephaestus-Experimental](https://github.com/ImaginaryNumb3r/Hephaestus-Experimental) but has since become its own repository due to increasing maturity and size. 

## Overview

This repository contains three main packages:
 - `parsing.model`: A utility package which defines tokens and nodes to create a [regular grammar](https://en.wikipedia.org/wiki/Regular_grammar).
 - `parsing.xml`: Package for parsing XML in a statical hierarchy.
 - `parsing.json`: Package for parsing Json in a statical hierarchy.

## Missing Features

This repository is a work in progress. Missing features are:
 - Data retrieving and manipulation for Jsons.
 - XML Streaming API for attributes.
 - Extensive testing for classes from `parsing.model` package.
 - Extensive testing for classes from `parsing.json` package.
 - Guidelines for writing docs.

## Code Guidelines
 - No type inference on literals (acceptable for single instances where all other variables are initialized as `var`)  
 - Comments are sentences. They start with a capital letter and end on a punctuation mark ('.', '!', '?').  
 - Wording: "get" is always an quick operation with O(1). If that is not the case, use "load" or "make".    
   - "Fetch" is like "get", but returns `java.lang.Optional`.  
   - "Put" is an action which does not create duplicate entries in a collection.  
   - When a method returns a mutable collection, it must be treated as property (and not be named get, set etc.)  
 - For immutable Value Objects, omitting the use of getters is encouraged.  
 - Reflection is discouraged but only strictly prohibited when meddling with foreign libraries.  
 - Be cautious in comments with wording: In the Java world, words like `final` have a different meaning and should not be used lightly.  
 - If one parameter is marked with nullability (via annotations), all parameters should be. For good style, probably all methods in the class should be.  
 - There is a difference between _must have_ and _good style_.  
 - Prefer speaking names for generics if applicable.  

# Why This Library?
Existing parsers:
 - Snaq: https://www.snaq.net/java/JCLAP/  
   Sample: https://www.snaq.net/java/JCLAP/  
 - Commons-CLI: http://commons.apache.org/proper/commons-cli/  
   Sample:  
 - Args4J: https://github.com/kohsuke/args4j  
   Sample: https://github.com/kohsuke/args4j/blob/master/args4j/examples/SampleMain.java  
 - JSAP 2.1: http://www.martiansoftware.com/jsap/  
   Sample: http://www.martiansoftware.com/jsap/doc/ch03s08.html  

List of all parsers: http://jewelcli.lexicalscope.com/related.html

No annotations, but typesafe, simple, easy to understand and cover a wide range of use-cases.
Covers simple parsing, as well as more sophisticated usages.

# Other things

Good Testing takes time, but it's worth its time in gold.
