/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

/**
 *
 * @author P11A-MANOHISOA
 */
public class NotParseableException extends Exception {

    /**
     * Creates a new instance of <code>NotParseableException</code> without detail message.
     */
    public NotParseableException() {
    }

    /**
     * Constructs an instance of <code>NotParseableException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotParseableException(String msg) {
        super(msg);
    }
}
