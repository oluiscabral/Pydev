/******************************************************************************
* Copyright (C) 2013  Fabio Zadrozny
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     Fabio Zadrozny <fabiofz@gmail.com> - initial API and implementation
******************************************************************************/
package org.python.pydev.shared_core.actions;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.python.pydev.shared_core.callbacks.ICallback;
import org.python.pydev.shared_core.string.FastStringBuffer;
import org.python.pydev.shared_core.string.ICoreTextSelection;
import org.python.pydev.shared_core.string.StringUtils;
import org.python.pydev.shared_core.string.TextSelectionUtils;
import org.python.pydev.shared_core.structure.Tuple;

public class LineCommentAction {

    private TextSelectionUtils ps;
    private String commentPattern;
    private int spacesInStart;

    // Either add '#' at the current indent or at the start of the line.
    private boolean addCommentsAtIndent;
    private ICallback<FastStringBuffer, String> commentLinesExec;

    public LineCommentAction(TextSelectionUtils ps, String commentPattern, int spacesInStart,
            boolean addCommentsAtIndent) {
        this.ps = ps;
        this.commentPattern = commentPattern;
        this.spacesInStart = spacesInStart;
        this.addCommentsAtIndent = addCommentsAtIndent;
        this.commentLinesExec = new ICallback<FastStringBuffer, String>() {
            @Override
            public FastStringBuffer call(String selectedText) {
                return commentLines(selectedText);
            }
        };
    }

    public LineCommentAction(TextSelectionUtils ps, String commentPattern, int spacesInStartComment,
            ICallback<FastStringBuffer, String> commentLinesExec) {
        this.ps = ps;
        this.commentPattern = commentPattern;
        this.spacesInStart = spacesInStartComment;
        this.commentLinesExec = commentLinesExec;
    }

    public FastStringBuffer commentLines(String selectedText) {
        List<String> ret = StringUtils.splitInLines(selectedText);
        FastStringBuffer strbuf = new FastStringBuffer(selectedText.length() + ret.size()
                + ((spacesInStart + 2) * ret.size()));
        if (ret.isEmpty()) {
            ret.add(selectedText);
        } else {
            if (selectedText.endsWith("\r") || selectedText.endsWith("\n")) {
                ret.add("");
            }
        }

        String spacesInStartComment = null;
        if (spacesInStart > 0) {
            spacesInStartComment = StringUtils.createSpaceString(spacesInStart);
        }

        // Used only when adding comments at the current indent.
        int lastFirstCharPosition = 0;
        FastStringBuffer lineBuf = new FastStringBuffer();
        boolean addSpacesInStartComment;

        for (String line : ret) {
            addSpacesInStartComment = true;
            if (addCommentsAtIndent) {
                lineBuf.clear();
                lineBuf.append(line);
                lineBuf.leftTrim();

                if (lineBuf.length() == 0) {
                    // Not even the '\n' at the end of the line remained.
                    addSpacesInStartComment = false;
                    lineBuf.append(line);
                    if (lastFirstCharPosition > 0) {
                        lineBuf.leftTrimSpacesAndTabs(); // i.e.: don't trim new lines this time.
                        strbuf.append(StringUtils.createSpaceString(lastFirstCharPosition));
                    } else {
                        // i.e.: empty line and there were no contents before, let's keep the current
                        // indent.
                        lineBuf.rightTrimNewLines();
                        lastFirstCharPosition = lineBuf.length();
                        strbuf.append(lineBuf);
                        lineBuf.clear();
                        lineBuf.append(line);
                        lineBuf.leftTrimSpacesAndTabs();
                    }
                } else {
                    strbuf.append(StringUtils.createSpaceString(line.length() - lineBuf.length()));
                    lastFirstCharPosition = TextSelectionUtils.getFirstCharPosition(line);
                }

                strbuf.append(commentPattern);
                if (spacesInStartComment != null && addSpacesInStartComment) {
                    strbuf.append(spacesInStartComment);
                }
                strbuf.append(lineBuf);
            } else {
                strbuf.append(commentPattern);
                if (spacesInStartComment != null) {
                    strbuf.append(spacesInStartComment);
                }
                strbuf.append(line);
            }
        }
        return strbuf;
    }

    public Tuple<Integer, Integer> execute() throws BadLocationException {
        // What we'll be replacing the selected text with

        // If they selected a partial line, count it as a full one
        ps.selectCompleteLine();

        String selectedText = ps.getSelectedText();

        FastStringBuffer strbuf = commentLinesExec.call(selectedText);
        ICoreTextSelection txtSel = ps.getTextSelection();
        int start = txtSel.getOffset();
        int len = txtSel.getLength();

        String replacement = strbuf.toString();
        // Replace the text with the modified information
        ps.getDoc().replace(start, len, replacement);
        return new Tuple<Integer, Integer>(start, replacement.length());
    }

}
