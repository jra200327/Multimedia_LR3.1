package org.iu3.documentviewer;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.ExpandedTitleContentHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DocumentProperty {
    private final String path;

    public DocumentProperty(String path) {
        this.path = path;
    }

    public String getHTML() throws IOException, TransformerConfigurationException, SAXException, TikaException {
        byte[] file = Files.readAllBytes(new File(path).toPath());
        AutoDetectParser tikaParser = new AutoDetectParser();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
        handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
        handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        handler.setResult(new StreamResult(out));
        
        ExpandedTitleContentHandler handler1 = new ExpandedTitleContentHandler(handler);
        tikaParser.parse(new ByteArrayInputStream(file), handler1, new Metadata(), new ParseContext());
        
        return out.toString("UTF-8");
    }
}