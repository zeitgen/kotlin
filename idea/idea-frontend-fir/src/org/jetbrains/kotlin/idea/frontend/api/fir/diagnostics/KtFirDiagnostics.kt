/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.frontend.api.fir.diagnostics

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiTypeElement
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.idea.frontend.api.diagnostics.KtDiagnosticWithPsi
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtClassLikeSymbol
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtSymbol
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtTypeParameterSymbol
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtVariableSymbol
import org.jetbrains.kotlin.idea.frontend.api.types.KtType
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtPropertyAccessor
import org.jetbrains.kotlin.psi.KtPropertyDelegate
import org.jetbrains.kotlin.psi.KtTypeReference

/*
 * This file was generated automatically
 * DO NOT MODIFY IT MANUALLY
 */

sealed class KtFirDiagnostic : KtDiagnosticWithPsi {
    abstract class Syntax : KtFirDiagnostic() {
    }

    abstract class OtherError : KtFirDiagnostic() {
    }

    abstract class IllegalConstExpression : KtFirDiagnostic() {
    }

    abstract class IllegalUnderscore : KtFirDiagnostic() {
    }

    abstract class ExpressionRequired : KtFirDiagnostic() {
    }

    abstract class BreakOrContinueOutsideALoop : KtFirDiagnostic() {
    }

    abstract class NotALoopLabel : KtFirDiagnostic() {
    }

    abstract class VariableExpected : KtFirDiagnostic() {
    }

    abstract class ReturnNotAllowed : KtFirDiagnostic() {
    }

    abstract class DelegationInInterface : KtFirDiagnostic() {
    }

    abstract class Hidden : KtFirDiagnostic() {
        abstract val hidden: KtSymbol
    }

    abstract class UnresolvedReference : KtFirDiagnostic() {
        abstract val reference: String
    }

    abstract class UnresolvedLabel : KtFirDiagnostic() {
    }

    abstract class DeserializationError : KtFirDiagnostic() {
    }

    abstract class ErrorFromJavaResolution : KtFirDiagnostic() {
    }

    abstract class UnknownCallableKind : KtFirDiagnostic() {
    }

    abstract class MissingStdlibClass : KtFirDiagnostic() {
    }

    abstract class NoThis : KtFirDiagnostic() {
    }

    abstract class SuperIsNotAnExpression : KtFirDiagnostic() {
    }

    abstract class SuperNotAvailable : KtFirDiagnostic() {
    }

    abstract class AbstractSuperCall : KtFirDiagnostic() {
    }

    abstract class InstanceAccessBeforeSuperCall : KtFirDiagnostic() {
        abstract val target: String
    }

    abstract class TypeParameterAsSupertype : KtFirDiagnostic() {
    }

    abstract class EnumAsSupertype : KtFirDiagnostic() {
    }

    abstract class RecursionInSupertypes : KtFirDiagnostic() {
    }

    abstract class NotASupertype : KtFirDiagnostic() {
    }

    abstract class SuperclassNotAccessibleFromInterface : KtFirDiagnostic() {
    }

    abstract class QualifiedSupertypeExtendedByOtherSupertype : KtFirDiagnostic() {
        abstract val otherSuperType: KtClassLikeSymbol
    }

    abstract class SupertypeInitializedInInterface : KtFirDiagnostic() {
    }

    abstract class InterfaceWithSuperclass : KtFirDiagnostic() {
    }

    abstract class ClassInSupertypeForEnum : KtFirDiagnostic() {
    }

    abstract class SealedSupertype : KtFirDiagnostic() {
    }

    abstract class SealedSupertypeInLocalClass : KtFirDiagnostic() {
    }

    abstract class ConstructorInObject : KtFirDiagnostic() {
        abstract override val psi: KtDeclaration
    }

    abstract class ConstructorInInterface : KtFirDiagnostic() {
        abstract override val psi: KtDeclaration
    }

    abstract class NonPrivateConstructorInEnum : KtFirDiagnostic() {
    }

    abstract class NonPrivateConstructorInSealed : KtFirDiagnostic() {
    }

    abstract class CyclicConstructorDelegationCall : KtFirDiagnostic() {
    }

    abstract class PrimaryConstructorDelegationCallExpected : KtFirDiagnostic() {
    }

    abstract class SupertypeInitializedWithoutPrimaryConstructor : KtFirDiagnostic() {
    }

    abstract class DelegationSuperCallInEnumConstructor : KtFirDiagnostic() {
    }

    abstract class PrimaryConstructorRequiredForDataClass : KtFirDiagnostic() {
    }

    abstract class ExplicitDelegationCallRequired : KtFirDiagnostic() {
    }

    abstract class SealedClassConstructorCall : KtFirDiagnostic() {
    }

    abstract class AnnotationArgumentKclassLiteralOfTypeParameterError : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class AnnotationArgumentMustBeConst : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class AnnotationArgumentMustBeEnumConst : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class AnnotationArgumentMustBeKclassLiteral : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class AnnotationClassMember : KtFirDiagnostic() {
    }

    abstract class AnnotationParameterDefaultValueMustBeConstant : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class InvalidTypeOfAnnotationMember : KtFirDiagnostic() {
        abstract override val psi: KtTypeReference
    }

    abstract class LocalAnnotationClassError : KtFirDiagnostic() {
        abstract override val psi: KtClassOrObject
    }

    abstract class MissingValOnAnnotationParameter : KtFirDiagnostic() {
        abstract override val psi: KtParameter
    }

    abstract class NonConstValUsedInConstantExpression : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class NotAnAnnotationClass : KtFirDiagnostic() {
        abstract val annotationName: String
    }

    abstract class NullableTypeOfAnnotationMember : KtFirDiagnostic() {
        abstract override val psi: KtTypeReference
    }

    abstract class VarAnnotationParameter : KtFirDiagnostic() {
        abstract override val psi: KtParameter
    }

    abstract class ExposedTypealiasExpandedType : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedFunctionReturnType : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedReceiverType : KtFirDiagnostic() {
        abstract override val psi: KtTypeReference
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedPropertyType : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedParameterType : KtFirDiagnostic() {
        abstract override val psi: KtParameter
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedSuperInterface : KtFirDiagnostic() {
        abstract override val psi: KtTypeReference
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedSuperClass : KtFirDiagnostic() {
        abstract override val psi: KtTypeReference
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class ExposedTypeParameterBound : KtFirDiagnostic() {
        abstract override val psi: KtTypeReference
        abstract val elementVisibility: Visibility
        abstract val restrictingDeclaration: KtSymbol
        abstract val restrictingVisibility: Visibility
    }

    abstract class InapplicableInfixModifier : KtFirDiagnostic() {
        abstract val modifier: String
    }

    abstract class RepeatedModifier : KtFirDiagnostic() {
        abstract val modifier: KtModifierKeywordToken
    }

    abstract class RedundantModifier : KtFirDiagnostic() {
        abstract val redundantModifier: KtModifierKeywordToken
        abstract val conflictingModifier: KtModifierKeywordToken
    }

    abstract class DeprecatedModifierPair : KtFirDiagnostic() {
        abstract val deprecatedModifier: KtModifierKeywordToken
        abstract val conflictingModifier: KtModifierKeywordToken
    }

    abstract class IncompatibleModifiers : KtFirDiagnostic() {
        abstract val modifier1: KtModifierKeywordToken
        abstract val modifier2: KtModifierKeywordToken
    }

    abstract class RedundantOpenInInterface : KtFirDiagnostic() {
        abstract override val psi: KtModifierListOwner
    }

    abstract class NoneApplicable : KtFirDiagnostic() {
        abstract val candidates: List<KtSymbol>
    }

    abstract class InapplicableCandidate : KtFirDiagnostic() {
        abstract val candidate: KtSymbol
    }

    abstract class InapplicableLateinitModifier : KtFirDiagnostic() {
        abstract val reason: String
    }

    abstract class Ambiguity : KtFirDiagnostic() {
        abstract val candidates: List<KtSymbol>
    }

    abstract class AssignOperatorAmbiguity : KtFirDiagnostic() {
        abstract val candidates: List<KtSymbol>
    }

    abstract class TypeMismatch : KtFirDiagnostic() {
        abstract val expectedType: KtType
        abstract val actualType: KtType
    }

    abstract class RecursionInImplicitTypes : KtFirDiagnostic() {
    }

    abstract class InferenceError : KtFirDiagnostic() {
    }

    abstract class ProjectionOnNonClassTypeArgument : KtFirDiagnostic() {
    }

    abstract class UpperBoundViolated : KtFirDiagnostic() {
        abstract val typeParameter: KtTypeParameterSymbol
        abstract val violatedType: KtType
    }

    abstract class TypeArgumentsNotAllowed : KtFirDiagnostic() {
    }

    abstract class WrongNumberOfTypeArguments : KtFirDiagnostic() {
        abstract val expectedCount: Int
        abstract val classifier: KtClassLikeSymbol
    }

    abstract class NoTypeForTypeParameter : KtFirDiagnostic() {
    }

    abstract class TypeParametersInObject : KtFirDiagnostic() {
    }

    abstract class IllegalProjectionUsage : KtFirDiagnostic() {
    }

    abstract class TypeParametersInEnum : KtFirDiagnostic() {
    }

    abstract class ConflictingProjection : KtFirDiagnostic() {
        abstract val type: String
    }

    abstract class VarianceOnTypeParameterNotAllowed : KtFirDiagnostic() {
    }

    abstract class ReturnTypeMismatchOnOverride : KtFirDiagnostic() {
        abstract val returnType: String
        abstract val superFunction: KtSymbol
    }

    abstract class PropertyTypeMismatchOnOverride : KtFirDiagnostic() {
        abstract val propertyType: String
        abstract val targetProperty: KtSymbol
    }

    abstract class VarTypeMismatchOnOverride : KtFirDiagnostic() {
        abstract val variableType: String
        abstract val targetVariable: KtSymbol
    }

    abstract class ManyCompanionObjects : KtFirDiagnostic() {
    }

    abstract class ConflictingOverloads : KtFirDiagnostic() {
        abstract val conflictingOverloads: String
    }

    abstract class Redeclaration : KtFirDiagnostic() {
        abstract val conflictingDeclaration: String
    }

    abstract class AnyMethodImplementedInInterface : KtFirDiagnostic() {
    }

    abstract class LocalObjectNotAllowed : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
        abstract val objectName: Name
    }

    abstract class LocalInterfaceNotAllowed : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
        abstract val interfaceName: Name
    }

    abstract class AbstractFunctionInNonAbstractClass : KtFirDiagnostic() {
        abstract override val psi: KtFunction
        abstract val function: KtSymbol
        abstract val containingClass: KtSymbol
    }

    abstract class AbstractFunctionWithBody : KtFirDiagnostic() {
        abstract override val psi: KtFunction
        abstract val function: KtSymbol
    }

    abstract class NonAbstractFunctionWithNoBody : KtFirDiagnostic() {
        abstract override val psi: KtFunction
        abstract val function: KtSymbol
    }

    abstract class PrivateFunctionWithNoBody : KtFirDiagnostic() {
        abstract override val psi: KtFunction
        abstract val function: KtSymbol
    }

    abstract class NonMemberFunctionNoBody : KtFirDiagnostic() {
        abstract override val psi: KtFunction
        abstract val function: KtSymbol
    }

    abstract class FunctionDeclarationWithNoName : KtFirDiagnostic() {
        abstract override val psi: KtFunction
    }

    abstract class AnonymousFunctionParameterWithDefaultValue : KtFirDiagnostic() {
        abstract override val psi: KtParameter
    }

    abstract class UselessVarargOnParameter : KtFirDiagnostic() {
        abstract override val psi: KtParameter
    }

    abstract class AbstractPropertyInNonAbstractClass : KtFirDiagnostic() {
        abstract override val psi: KtModifierListOwner
        abstract val property: KtSymbol
        abstract val containingClass: KtSymbol
    }

    abstract class PrivatePropertyInInterface : KtFirDiagnostic() {
        abstract override val psi: KtProperty
    }

    abstract class AbstractPropertyWithInitializer : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class PropertyInitializerInInterface : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class PropertyWithNoTypeNoInitializer : KtFirDiagnostic() {
        abstract override val psi: KtProperty
    }

    abstract class AbstractDelegatedProperty : KtFirDiagnostic() {
        abstract override val psi: KtPropertyDelegate
    }

    abstract class DelegatedPropertyInInterface : KtFirDiagnostic() {
        abstract override val psi: KtPropertyDelegate
    }

    abstract class AbstractPropertyWithGetter : KtFirDiagnostic() {
        abstract override val psi: KtPropertyAccessor
    }

    abstract class AbstractPropertyWithSetter : KtFirDiagnostic() {
        abstract override val psi: KtPropertyAccessor
    }

    abstract class PrivateSetterForAbstractProperty : KtFirDiagnostic() {
    }

    abstract class PrivateSetterForOpenProperty : KtFirDiagnostic() {
    }

    abstract class ExpectedPrivateDeclaration : KtFirDiagnostic() {
        abstract override val psi: KtModifierListOwner
    }

    abstract class ExpectedDeclarationWithBody : KtFirDiagnostic() {
        abstract override val psi: KtDeclaration
    }

    abstract class ExpectedPropertyInitializer : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class ExpectedDelegatedProperty : KtFirDiagnostic() {
        abstract override val psi: KtPropertyDelegate
    }

    abstract class InitializerRequiredForDestructuringDeclaration : KtFirDiagnostic() {
        abstract override val psi: KtDestructuringDeclaration
    }

    abstract class ComponentFunctionMissing : KtFirDiagnostic() {
        abstract val missingFunctionName: Name
        abstract val destructingType: KtType
    }

    abstract class ComponentFunctionAmbiguity : KtFirDiagnostic() {
        abstract val functionWithAmbiguityName: Name
        abstract val candidates: List<KtSymbol>
    }

    abstract class UninitializedVariable : KtFirDiagnostic() {
        abstract val variable: KtVariableSymbol
    }

    abstract class WrongInvocationKind : KtFirDiagnostic() {
        abstract val declaration: KtSymbol
        abstract val requiredRange: EventOccurrencesRange
        abstract val actualRange: EventOccurrencesRange
    }

    abstract class LeakedInPlaceLambda : KtFirDiagnostic() {
        abstract val lambda: KtSymbol
    }

    abstract class WrongImpliesCondition : KtFirDiagnostic() {
    }

    abstract class RedundantVisibilityModifier : KtFirDiagnostic() {
        abstract override val psi: KtModifierListOwner
    }

    abstract class RedundantModalityModifier : KtFirDiagnostic() {
        abstract override val psi: KtModifierListOwner
    }

    abstract class RedundantReturnUnitType : KtFirDiagnostic() {
        abstract override val psi: PsiTypeElement
    }

    abstract class RedundantExplicitType : KtFirDiagnostic() {
    }

    abstract class RedundantSingleExpressionStringTemplate : KtFirDiagnostic() {
    }

    abstract class CanBeVal : KtFirDiagnostic() {
        abstract override val psi: KtDeclaration
    }

    abstract class CanBeReplacedWithOperatorAssignment : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class RedundantCallOfConversionMethod : KtFirDiagnostic() {
    }

    abstract class ArrayEqualityOperatorCanBeReplacedWithEquals : KtFirDiagnostic() {
        abstract override val psi: KtExpression
    }

    abstract class EmptyRange : KtFirDiagnostic() {
    }

    abstract class RedundantSetterParameterType : KtFirDiagnostic() {
    }

    abstract class UnusedVariable : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
    }

    abstract class AssignedValueIsNeverRead : KtFirDiagnostic() {
    }

    abstract class VariableInitializerIsRedundant : KtFirDiagnostic() {
    }

    abstract class VariableNeverRead : KtFirDiagnostic() {
        abstract override val psi: KtNamedDeclaration
    }

    abstract class UselessCallOnNotNull : KtFirDiagnostic() {
    }

}
