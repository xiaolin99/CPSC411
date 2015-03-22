all: parser.class

parser.class: mpp_lexor.java parser.java sym.java
	javac -cp java_cup-runtime.jar *java

mpp_lexor.java: mpp_lexor.flex
	jflex mpp_lexor.flex

parser.java: mpp_parser.cup
	java -jar java_cup.jar mpp_parser.cup

sym.java: mpp_parser.cup
	java -jar java_cup.jar mpp_parser.cup

clean:
	rm -rf *~ *class *log

clean-all:
	rm -rf *~ *class *log parser.java sym.java mpp_lexor.java

run:
	java -cp java_cup.jar:. parser
