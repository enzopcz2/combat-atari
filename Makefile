MAIN = Combat
SRC_DIR = src
JAVAC = javac
JAVA = java
all:
	$(JAVAC) -cp $(SRC_DIR) $(SRC_DIR)/*.java

run:
	$(JAVA) -cp $(SRC_DIR) $(MAIN)

