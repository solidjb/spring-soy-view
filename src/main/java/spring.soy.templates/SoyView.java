package spring.soy.templates;

import com.google.template.soy.tofu.SoyTofu;
import org.springframework.web.servlet.view.AbstractTemplateView;
import spring.soy.templates.bundle.SoyMsgBundleResolver;
import spring.soy.templates.data.ModelToSoyDataConverter;
import spring.soy.templates.locale.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mati
 * Date: 19/06/2013
 * Time: 23:32
 */
public class SoyView extends AbstractTemplateView {

    private SoyTofu compiledTemplates;

    private String templateName;

    private SoyMsgBundleResolver soyMsgBundleResolver;

    private LocaleResolver localeResolver;

    private ModelToSoyDataConverter modelToSoyDataConverter;

    @Override
    protected void renderMergedTemplateModel(final Map<String, Object> model,
                                             final HttpServletRequest request,
                                             final HttpServletResponse response) throws Exception {
        final Writer writer = response.getWriter();

        final Map<String, ?> soyData = modelToSoyDataConverter.convert(model);

        final Locale locale = localeResolver.resolveLocale(request);

        compiledTemplates.newRenderer(templateName)
                .setData(soyData)
                .setMsgBundle(soyMsgBundleResolver.resolve(locale))
                .render(writer);

        writer.flush();
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public void setCompiledTemplates(final SoyTofu compiledTemplates) {
        this.compiledTemplates = compiledTemplates;
    }

    public void setSoyMsgBundleResolver(final SoyMsgBundleResolver soyMsgBundleResolver) {
        this.soyMsgBundleResolver = soyMsgBundleResolver;
    }

    public void setLocaleResolver(final LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public void setModelToSoyDataConverter(ModelToSoyDataConverter modelToSoyDataConverter) {
        this.modelToSoyDataConverter = modelToSoyDataConverter;
    }

}
