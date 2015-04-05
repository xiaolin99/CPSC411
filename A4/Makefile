all: parser.class

parser.class: m_lexor.java parser.java sym.java SymbolChecker.java SymbolTable.java
	javac -cp java_cup-runtime.jar *java

m_lexor.java: m_lexor.flex
	jflex m_lexor.flex

parser.java: m_parser.cup
	java -jar java_cup.jar m_parser.cup

sym.java: m_parser.cup
	java -jar java_cup.jar m_parser.cup

clean:
	rm -rf *~ *class *log

clean-all:
	rm -rf *~ *class *log parser.java sym.java m_lexor.java

run:
	java -cp java_cup.jar:. parser
