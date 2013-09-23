package com.seventh7.mybatis.util;

import com.google.common.base.Optional;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.seventh7.mybatis.Annotation;
import com.seventh7.mybatis.dom.model.IdDomElement;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author yanglin
 */
public final class JavaUtils {

  private JavaUtils() {
    throw new UnsupportedOperationException();
  }

  public static boolean isElementWithinInterface(@Nullable PsiElement element) {
    PsiClass type = PsiTreeUtil.getParentOfType(element, PsiClass.class);
    return Optional.fromNullable(type).isPresent() && type.isInterface();
  }

  @NotNull
  public static Optional<PsiClass> findClzz(@NotNull Project project, @NotNull String clzzName) {
    return Optional.fromNullable(JavaPsiFacade.getInstance(project).findClass(clzzName, GlobalSearchScope.allScope(project)));
  }

  public static Optional<PsiMethod> findMethod(@NotNull Project project, @Nullable String clzzName, @Nullable String methodName) {
    if (StringUtils.isBlank(clzzName) && StringUtils.isBlank(methodName)) {
      return Optional.absent();
    }
    Optional<PsiClass> clzz = findClzz(project, clzzName);
    if (clzz.isPresent()) {
      PsiMethod[] methods = clzz.get().findMethodsByName(methodName, true);
      return ArrayUtils.isEmpty(methods) ? Optional.<PsiMethod>absent() : Optional.of(methods[0]);
    }
    return Optional.absent();
  }

  public static Optional<PsiMethod> findMethod(@NotNull Project project, @NotNull IdDomElement element) {
    return findMethod(project, MapperUtils.getNamespace(element), MapperUtils.getId(element));
  }

  public static boolean isAnnotationPresent(@NotNull PsiModifierListOwner target, @NotNull Annotation annotation) {
    return null != target.getModifierList().findAnnotation(annotation.getQualifiedName());
  }

  public static Optional<PsiAnnotation> getPsiAnnotation(@NotNull PsiModifierListOwner target, @NotNull Annotation annotation) {
    return Optional.fromNullable(target.getModifierList().findAnnotation(annotation.getQualifiedName()));
  }

  public static boolean isAnyAnnotationPresent(@NotNull PsiModifierListOwner target, @NotNull Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      if (isAnnotationPresent(target, annotation)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isAllParameterWithAnnotation(@NotNull PsiMethod method, @NotNull Annotation annotation) {
    PsiParameter[] parameters = method.getParameterList().getParameters();
    for (PsiParameter parameter : parameters) {
      if (!isAnnotationPresent(parameter, annotation)) {
        return false;
      }
    }
    return true;
  }

  public static boolean hasImportClzz(PsiJavaFile file, String clzzName) {
    PsiImportStatement[] statements = file.getImportList().getImportStatements();

    for (PsiImportStatement tmp : statements) {
      if (tmp.getQualifiedName().equals(clzzName)) {
        return true;
      }
    }
    return false;
  }

}