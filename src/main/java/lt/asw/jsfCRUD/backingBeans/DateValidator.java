package lt.asw.jsfCRUD.backingBeans;

import javax.faces.application.FacesMessage;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ResourceBundle;


@FacesValidator("lt.asw.jsfCRUD.backingBeans.DateValidator")
public class DateValidator implements Validator {

    SimpleDateFormat sdf;
    Date sartDate;
    Date endDate;

    public DateValidator() throws ParseException {

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sartDate = sdf.parse("1900-01-01");
            endDate = sdf.parse("2009-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent component,
                         Object value) throws ValidatorException {

        Date dateToValidate = (Date) value;
        ResourceBundle bundle = ResourceBundle.getBundle("lt.asw.jsfCRUD.student", facesContext.getViewRoot().getLocale());

        if (dateToValidate.before(sartDate)){
            FacesMessage msg =
                    new FacesMessage(bundle.getString("valid.msg.dateSummary"),
                            bundle.getString("valid.msg.minDateDetails"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        if (dateToValidate.after(endDate)){
            FacesMessage msg =
                    new FacesMessage(bundle.getString("valid.msg.dateSummary"),
                            bundle.getString("valid.msg.maxDateDetails"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

//@FacesValidator("lt.asw.jsfCRUD.backingBeans.DateValidator")
//public class DateValidator implements Validator {
//
//    private static final String DATE_PATTERN = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
//
//    private Pattern pattern;
//    private Matcher matcher;
//
//    public void EmailValidator(){
//        pattern = Pattern.compile(DATE_PATTERN);
//
//    }
//
//
//}
