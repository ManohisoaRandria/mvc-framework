/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author manohisoa
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadParam {

    String[] allowedType() default {};

    String fileFolder() default "";

    int maxFileSize() default 1000 * 1024;//1.024mo
//    'txt' => 'text/plain',
//            'htm' => 'text/html',
//            'html' => 'text/html',
//            'php' => 'text/html',
//            'css' => 'text/css',
//            'js' => 'application/javascript',
//            'json' => 'application/json',
//            'xml' => 'application/xml',
//            'swf' => 'application/x-shockwave-flash',
//            'flv' => 'video/x-flv',
//
//            // images
//            'png' => 'image/png',
//            'jpe' => 'image/jpeg',
//            'jpeg' => 'image/jpeg',
//            'jpg' => 'image/jpeg',
//            'gif' => 'image/gif',
//            'bmp' => 'image/bmp',
//            'ico' => 'image/vnd.microsoft.icon',
//            'tiff' => 'image/tiff',
//            'tif' => 'image/tiff',
//            'svg' => 'image/svg+xml',
//            'svgz' => 'image/svg+xml',
//
//            // archives
//            'zip' => 'application/zip',
//            'rar' => 'application/x-rar-compressed',
//            'exe' => 'application/x-msdownload',
//            'msi' => 'application/x-msdownload',
//            'cab' => 'application/vnd.ms-cab-compressed',
//
//            // audio/video
//            'mp3' => 'audio/mpeg',
//            'qt' => 'video/quicktime',
//            'mov' => 'video/quicktime',
//
//            // adobe
//            'pdf' => 'application/pdf',
//            'psd' => 'image/vnd.adobe.photoshop',
//            'ai' => 'application/postscript',
//            'eps' => 'application/postscript',
//            'ps' => 'application/postscript',
//
//            // ms office
//            'doc' => 'application/msword',
//            'rtf' => 'application/rtf',
//            'xls' => 'application/vnd.ms-excel',
//            'ppt' => 'application/vnd.ms-powerpoint',
//
//            // open office
//            'odt' => 'application/vnd.oasis.opendocument.text',
//            'ods' => 'application/vnd.oasis.opendocument.spreadsheet',
}
