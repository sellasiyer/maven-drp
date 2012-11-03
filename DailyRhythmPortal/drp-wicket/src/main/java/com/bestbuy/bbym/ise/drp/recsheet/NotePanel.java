package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;

public abstract class NotePanel extends ModalPanel {

    private final AjaxSubmitLink done;
    private final Form form;
    private static final long serialVersionUID = 1L;

    private final TextArea<String> notes;

    private String noteText;

    public String getNoteText() {
	return noteText;
    }

    public void setNoteText(String noteText) {
	this.noteText = noteText;
    }

    public NotePanel(String id) {
	super(id);

	form = new Form("noteForm");

	add(form);

	notes = new TextArea("notes", new PropertyModel<String>(this, "noteText"));
	notes.setOutputMarkupId(true);
	notes.setMarkupId("notes");
	form.add(notes);

	done = new AjaxSubmitLink("done", form) {

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		close(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		super.onError(target, form);
	    }
	};
	done.setOutputMarkupId(true);
	done.setMarkupId("done");

	form.add(done);
    }

}
