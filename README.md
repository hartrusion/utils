# Utils
Common used classes for my projects. Contains some helper classes and a 
model view controller implementation for swing applications. As I'm just an 
engineer and no experienced java programmer, this might not be of use for 
you at all.

**Please note: This is work in progess. It's propably not good code and only
published to have easy access to it and disclose source code of my projects
for security reasons.** I strongly believe that beeing able to disclose the 
full source and allow people to complile and build projects themselves is
a proper way to prove that an application does not pose any risks.

### ArrayExt
I use java for doing things I was doing in MATLAB/Octave before and had to code 
some small helper functions that make the use of arrays a little easier. Those 
array extensions allow quick modification of arrays. It is not that common as 
java provides collections for such cases.

### MVC Package
The MVC package contains a model view controller implementation that is designed
for controlling an application with a swing UI. It uses strings to identify 
property changes and actions and allows thread safe use of swing.