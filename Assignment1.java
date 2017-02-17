
class Assignment1 { //brace goes on same line...
    public static void main (String args[]) throws Exception { //brace goes on same line...
        if (args.length < 3) {//brace goes on same line...
            System.out.println("Usage: java Assignment1 <input dir> <output file> <mapping file>");
        }
        else {//brace goes on same line...
            IRSystem ir = new IRSystem();
            ir.buildIndex(args[0]);
            ir.writeIndex(args[1]);
            ir.writeDocumentIDs(args[2]);
        }
    }
}
