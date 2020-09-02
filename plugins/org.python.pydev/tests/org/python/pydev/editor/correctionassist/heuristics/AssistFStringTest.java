/**
 * Copyright (c) 2020 by Brainwy Software Ltda
 * Licensed under the terms of the Eclipse Public License (EPL).
 * Please see the license.txt included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package org.python.pydev.editor.correctionassist.heuristics;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.python.pydev.core.MisconfigurationException;
import org.python.pydev.core.docutils.PySelection;
import org.python.pydev.core.proposals.CompletionProposalFactory;
import org.python.pydev.editor.actions.PyAction;
import org.python.pydev.editor.codecompletion.proposals.DefaultCompletionProposalFactory;
import org.python.pydev.shared_core.code_completion.ICompletionProposalHandle;

import junit.framework.TestCase;

public class AssistFStringTest extends TestCase {
    private AssistFString assist;

    public static void main(String[] args) {
        try {
            AssistFStringTest test = new AssistFStringTest();
            test.setUp();
            test.tearDown();
            junit.textui.TestRunner.run(AssistFStringTest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        assist = new AssistFString();
        CompletionProposalFactory.set(new DefaultCompletionProposalFactory());
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        CompletionProposalFactory.set(null);
    }

    void check(String docContents, int offset, String expectedDoc)
            throws BadLocationException, MisconfigurationException {
        Document doc = new Document(docContents);
        PySelection ps = new PySelection(doc, offset);
        String sel = PyAction.getLineWithoutComments(ps);
        assertEquals(true, assist.isValid(ps, sel, null, offset));
        List<ICompletionProposalHandle> props = assist.getProps(ps, null, null, null, null, offset);
        assertEquals(1, props.size());
        assertEquals("Convert to f-string", props.get(0).getDisplayString());
        props.get(0).apply(doc);
        assertEquals(expectedDoc, doc.get());
    }

    void checkNoCompletions(String docContents, int offset) throws BadLocationException, MisconfigurationException {
        Document doc = new Document(docContents);
        PySelection ps = new PySelection(doc, offset);
        String sel = PyAction.getLineWithoutComments(ps);
        assertEquals(true, assist.isValid(ps, sel, null, offset));
        List<ICompletionProposalHandle> props = assist.getProps(ps, null, null, null, null, offset);
        assertEquals(0, props.size());
    }

    public void testSimple() throws BadLocationException, MisconfigurationException {
        String d = "print('''a = %s %s''' % (a, b,))\n" +
                "\n" +
                "x = 20";
        String e = "print(f'''a = {a} {b}''')\n" +
                "\n" +
                "x = 20";
        check(d, 10, e);
    }

    public void testSimple2() throws BadLocationException, MisconfigurationException {
        String d = "x = 'a = %s' % some_name";
        String e = "x = f'a = {some_name}'";
        check(d, 10, e);
    }

    public void testSimple3() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s\" % some_name.another_name('ignore,anything,here')";
        String e = "f\"a = {some_name.another_name('ignore,anything,here')}\"";
        check(d, 5, e);
    }

    public void testSimple4() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s\" % some_name[:2]";
        String e = "f\"a = {some_name[:2]}\"";
        check(d, 5, e);
    }

    public void testSimple5() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % (a , c)";
        String e = "f\"a = {a} {c}\"";
        check(d, 10, e);
    }

    public void testSimple6() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % some_name";
        String e = "f\"a = {some_name[0]} {some_name[1]}\"";
        check(d, 10, e);
    }

    public void testSimple7() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s\" % some_name.another_name('ignore,anything,here').another_name2('ignore, this, here')";
        String e = "f\"a = {some_name.another_name('ignore,anything,here').another_name2('ignore, this, here')}\"";
        check(d, 5, e);
    }

    public void testSimple8() throws BadLocationException, MisconfigurationException {
        String d = "\"%s - %s\" % (\n" +
                "  a,\n" +
                "  b\n" +
                ")";
        String e = "f\"{a} - {b}\"";
    }

    public void testSimple9() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % ('something' , 'foobar')";
        String e = "f\"a = {'something'} {'foobar'}\"";
        check(d, 10, e);
    }

    public void testSimple10() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % (\"something\", \"foobar\")";
        String e = "f\"a = {\"something\"} {\"foobar\"}\"";
        check(d, 10, e);
    }

    public void testSimple11() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % (some_str[:-1], some_str2[-1:])";
        String e = "f\"a = {some_str[:-1]} {some_str2[-1:]}\"";
        check(d, 10, e);
    }

    public void testSimple12() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % ({'id': 1, 'value':10}, {'id': 2, 'value':20})";
        String e = "f\"a = {{'id': 1, 'value':10}} {{'id': 2, 'value':20}}\"";
        check(d, 10, e);
    }

    public void testSimple13() throws BadLocationException, MisconfigurationException {
        String d = "call('%s %s' % foo, bar)";
        String e = "call(f'{foo} {bar}')";
        check(d, 10, e);
    }

    public void testSimple14() throws BadLocationException, MisconfigurationException {
        String d = "call('%s %s' % foo, bar)\n" +
                "\n" +
                "x = 10";
        String e = "call(f'{foo} {bar}')\n" +
                "\n" +
                "x = 10";
        check(d, 10, e);
    }

    public void testSimple15() throws BadLocationException, MisconfigurationException {
        String d = "print('''a = %s %s''' % (a, b,))\n" +
                "\n" +
                "x = 10";
        String e = "print(f'''a = {a} {b}''')\n" +
                "\n" +
                "x = 10";
        check(d, 10, e);
    }

    public void testSimple16() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % (\n" +
                "\"something\", \n" +
                "\"foobar\"\n" +
                ")";
        String e = "f\"a = {\"something\"} {\"foobar\"}\"";
        check(d, 10, e);
    }

    public void testSimple17() throws BadLocationException, MisconfigurationException {
        // this should give no suggestion nor error
        String d = "\"a = %s\" % {'id': 1, 'value':10}";
        checkNoCompletions(d, 5);
    }

    public void testSimple18() throws BadLocationException, MisconfigurationException {
        String d = "\"a = %s %s\" % (\n" +
                "some_str[:-1], \n" +
                "some_str2[-1:]\n" +
                ")\n"
                + "\n"
                + "x = 10";
        String e = "f\"a = {some_str[:-1]} {some_str2[-1:]}\"\n" +
                "\n" +
                "x = 10";
        check(d, 10, e);
    }

    public void testSimple19() throws BadLocationException, MisconfigurationException {
        String d = "call('%s %s' % foo, bar,)\n" +
                "\n" +
                "x = 10";
        String e = "call(f'{foo} {bar}')\n" +
                "\n" +
                "x = 10";
        check(d, 10, e);
    }

    public void testSimple20() throws BadLocationException, MisconfigurationException {
        // this should give no suggestion nor error
        String d = "'%s' % (somevar";
        checkNoCompletions(d, 10);
    }

    public void testSimple21() throws BadLocationException, MisconfigurationException {
        // this should give no suggestion nor error
        String d = "''%s' % test[";
        checkNoCompletions(d, 10);
    }

    public void testSimple22() throws BadLocationException, MisconfigurationException {
        String d = "def method(foo):\n" +
                "    '%s %s' % foo\n" +
                "    \n" +
                "def method2():\n" +
                "    pass";
        String e = "def method(foo):\n" +
                "    f'{foo[0]} {foo[1]}'\n" +
                "    \n" +
                "def method2():\n" +
                "    pass";
        check(d, 25, e);
    }
}
