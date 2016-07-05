POM=	pom.xml
TARG=	target
DOC=	$(TARG)/doc

#all:	package
all:	doc

.PHONEY:
package:	$(TARG)

.PHONEY:
deploy:
	lein deploy clojars

.PHONEY:
doc:		$(DOC)

$(DOC):
	lein javadoc
	lein codox

$(TARG):	$(POM)
	lein jar

$(POM):		project.clj
	lein pom

clean:
	rm -fr target dev-resources $(POM)*
	rmdir test 2>/dev/null || true
