all: parser.class

parser.class: mpp_lexor.java parser.java sym.java
	javac -cp /usr/share/java/java_cup-runtime.jar *java

mpp_lexor.java: mpp_lexor.flex
	jflex mpp_lexor.flex

parser.java: mpp_parser.cup
	java -jar /usr/share/java/java_cup.jar mpp_parser.cup

sym.java: mpp_parser.cup
	java -jar /usr/share/java/java_cup.jar mpp_parser.cup

clean:
	rm -rf *~ *class

clean-all:
	rm -rf *~ *class *java

run:
	java -cp /usr/share/java/java_cup.jar:. parser
