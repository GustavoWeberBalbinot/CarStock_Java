package projetoA3;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FiltroDeCaracteres extends PlainDocument {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum TipoDeFiltro {
		numerosInt,
		numerosDouble,
		nome,
		email,
		data
    }

    private TipoDeFiltro tipoDeFiltro;
    private int limiteDeCaracteres;

    public FiltroDeCaracteres(TipoDeFiltro tipoDeFiltro, int limiteDeCaracteres) {
        this.tipoDeFiltro = tipoDeFiltro;
        this.limiteDeCaracteres = limiteDeCaracteres;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // Verifique se o número de caracteres inseridos não excede o limite
        if (getLength() + str.length() > limiteDeCaracteres) {
            return;
        }

        // Filtre os caracteres com base no tipo de filtro
        switch (tipoDeFiltro) {
		//O "^"significa exceto, ou seja, ele vai tirar todos os outros exceto o que eu coloquei
		//Para numeros
		case numerosInt: str = str.replaceAll("[^0-9]", ""); break;
			
		//Para numeros decimais
		case numerosDouble: str = str.replaceAll("[^0-9,.]", ""); break;
	
		//Para nomes(pega tudos os caracteres do latim e o espaço
		case nome: str = str.replaceAll("[^\\p{IsLatin} ]", ""); break;
	
		//Para o email
		case email: str = str.replaceAll("[^\\p{IsLatin}@.\\-_][^0-9]", ""); break;
		
		//Para a data
		case data: str = str.replaceAll("[^0-9/-]", ""); break;
        }

        super.insertString(offs, str, a);
    }
}
