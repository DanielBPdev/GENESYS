<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "title">
        ${msg("updatePasswordTitle")}
    <#elseif section = "header">
        ${msg("updatePasswordTitle")}
    <#elseif section = "form">
    <section class="content_login">
            <div class="row">
              <div class="col-xs-12 col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2">
              <fieldset>
        <form id="kc-passwd-update-form" class="form-signin" action="${url.loginAction}" method="post">
            <h3 class="form-signin-heading"">Cambiar contraseña</h3>
            <legend><h4 class="form-signin-heading" >Digite su nueva clave</h4></legend>
            <div id="error-kc-content"  style="display:none" class="${properties.kcContentClass!}">
                            <div id="kc-content-wrapper" class="${properties.kcContentWrapperClass!}">
                                
                                    <div class="${properties.kcFeedbackAreaClass!}">
                                        <div class="alert alert-error">
                                            <span id="mensajeError" class="kc-feedback-text"></span>
                                        </div>
                                    </div>
                                
                            </div>
                        </div>
            <input type="text" readonly value="this is not a login form" style="display: none;">
            <input type="password" readonly value="this is not a login form" style="display: none;">

            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="password-new" class="sr-only">${msg("passwordNew")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="password" id="password-new" name="password-new" class="form-control" autofocus autocomplete="off" placeholder="Contraseña nueva" required/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="password-confirm" class="sr-only">${msg("passwordConfirm")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="password" id="password-confirm" name="password-confirm" class="form-control" autocomplete="off"  placeholder="Confirmar contraseña nueva" required/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                    </div>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <input class="btn btn-block btn-primary center-block" name="login" id="kc-login" type="submit" value="Enviar"/>
                </div>
            </div>
        </form>
         </fieldset>
        </div>
            </div>

        </section>

    </#if>
</@layout.registrationLayout>