package Exceptions;

    public class NameFalschException extends DatenbankFehlerException {


        public NameFalschException(){
            super();
        }

        public NameFalschException(String message) {
            super(message);
        }

}
