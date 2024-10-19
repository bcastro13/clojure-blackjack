FORCE:

commit: clean lint
	make splint && \
	make tests && \
	git add .
	git commit -a

clean: FORCE
	lein clean

prod: FORCE commit
	git push

tests: FORCE
	lein eftest

lint: FORCE ancient eastwood clj_kondo cljfmt

eastwood: FORCE
	lein eastwood 

splint: FORCE
	lein splint src/ tests/

cljfmt: FORCE
	lein cljfmt fix

clj_kondo: FORCE
	rm -f .clj-kondo/com.github.seancorfield/next.jdbc/hooks/com/github/seancorfield/next_jdbc.clj_kondo && \
	clj-kondo --parallel --lint src/ tests/

cloverage: 
	lein cloverage

ancient: FORCE
	lein ancient

ancient_upgrade: FORCE
	lein ancient upgrade

