package listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//		programmatically adding retry analyzer for all failed tests only if defined under testng.xml file under <listeners> tag
		annotation.setRetryAnalyzer(TestListener.class);
	}
}
