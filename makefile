USER=		plandes
PROJ=		tabres
POM=		pom.xml
TARG=		target
DOC_DIR=	doc

all:	doc

.PHONEY:
package:	$(TARG) $(DOC_DIR)

.PHONEY:
deploy:
	lein deploy clojars

# https://github.com/weavejester/codox/wiki/Deploying-to-GitHub-Pages
$(DOC_DIR):
	rm -rf $(DOC_DIR) && mkdir $(DOC_DIR)
	git clone https://github.com/$(USER)/$(PROJ).git $(DOC_DIR)
	( cd doc ; \
	  git symbolic-ref HEAD refs/heads/gh-pages ; \
	  rm .git/index ; \
	  git clean -fdx )
	lein javadoc
	lein codox
	( cd doc ; \
	  git add . ; \
	  git commit -am "new doc push" ; \
	  git push -u origin gh-pages )

$(TARG):
	lein jar

$(POM):		project.clj
	lein pom

clean:
	rm -fr dev-resources $(POM)* $(DOC_DIR) $(TARG)
	rmdir test 2>/dev/null || true
	rmdir resources 2>/dev/null || true
