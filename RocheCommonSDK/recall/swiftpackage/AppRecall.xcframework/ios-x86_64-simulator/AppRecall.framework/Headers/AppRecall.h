#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class AppRecallAppRecallResponse, AppRecallKotlinThrowable, AppRecallKotlinArray<T>, AppRecallKotlinException, AppRecallSamdResponse, AppRecallKtor_client_coreHttpRequestData, AppRecallKtor_client_coreHttpResponseData, AppRecallKtor_client_coreHttpClient, AppRecallKtor_client_coreHttpClientEngineConfig, AppRecallKotlinx_coroutines_coreCoroutineDispatcher, AppRecallKotlinRuntimeException, AppRecallKotlinIllegalStateException, AppRecallKotlinx_serialization_coreSerializersModule, AppRecallKotlinx_serialization_coreSerialKind, AppRecallKotlinNothing, AppRecallKtor_httpUrl, AppRecallKtor_httpHttpMethod, AppRecallKtor_httpOutgoingContent, AppRecallKtor_httpHttpStatusCode, AppRecallKtor_utilsGMTDate, AppRecallKtor_httpHttpProtocolVersion, AppRecallKtor_client_coreHttpClientConfig<T>, AppRecallKtor_client_coreHttpRequestBuilder, AppRecallKtor_client_coreHttpClientCall, AppRecallKtor_client_coreHttpReceivePipeline, AppRecallKtor_client_coreHttpRequestPipeline, AppRecallKtor_client_coreHttpResponsePipeline, AppRecallKtor_client_coreHttpSendPipeline, AppRecallKtor_client_coreProxyConfig, AppRecallKotlinAbstractCoroutineContextElement, AppRecallKtor_httpURLProtocol, AppRecallKtor_utilsAttributeKey<T>, AppRecallKtor_httpContentType, AppRecallKotlinCancellationException, AppRecallKotlinUnit, AppRecallKtor_utilsWeekDay, AppRecallKtor_utilsMonth, AppRecallKtor_httpHeadersBuilder, AppRecallKtor_httpURLBuilder, AppRecallKtor_client_coreTypeInfo, AppRecallKtor_client_coreHttpResponse, AppRecallKtor_utilsPipelinePhase, AppRecallKtor_utilsPipeline<TSubject, TContext>, AppRecallKtor_client_coreHttpResponseContainer, AppRecallKtor_httpUrlEncodingOption, AppRecallKtor_httpHeaderValueParam, AppRecallKtor_httpHeaderValueWithParameters, AppRecallKotlinEnum<E>, AppRecallKtor_utilsStringValuesBuilder, AppRecallKtor_httpParametersBuilder, AppRecallKtor_ioMemory, AppRecallKtor_ioIoBuffer, AppRecallKotlinByteArray, AppRecallKtor_ioByteReadPacket, AppRecallKtor_ioByteOrder, AppRecallKotlinx_coroutines_coreAtomicDesc, AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp, AppRecallKtor_ioBuffer, AppRecallKtor_ioChunkBuffer, AppRecallKotlinCharArray, AppRecallKotlinByteIterator, AppRecallKtor_ioAbstractInput, AppRecallKtor_ioByteReadPacketBase, AppRecallKtor_ioByteReadPacketPlatformBase, AppRecallKotlinKTypeProjection, AppRecallKotlinx_coroutines_coreAtomicOp<__contravariant T>, AppRecallKotlinx_coroutines_coreOpDescriptor, AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode, AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc, AppRecallKotlinCharIterator, AppRecallKotlinKVariance, AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc<T>, AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc<T>;

@protocol AppRecallKotlinx_serialization_coreKSerializer, AppRecallKtor_client_coreHttpClientEngine, AppRecallKotlinx_serialization_coreEncoder, AppRecallKotlinx_serialization_coreSerialDescriptor, AppRecallKotlinx_serialization_coreSerializationStrategy, AppRecallKotlinx_serialization_coreDecoder, AppRecallKotlinx_serialization_coreDeserializationStrategy, AppRecallKotlinIterator, AppRecallKtor_client_coreHttpClientEngineCapability, AppRecallKotlinCoroutineContext, AppRecallKotlinx_coroutines_coreCoroutineScope, AppRecallKtor_ioCloseable, AppRecallKotlinx_serialization_coreCompositeEncoder, AppRecallKotlinAnnotation, AppRecallKotlinx_serialization_coreCompositeDecoder, AppRecallKtor_httpHeaders, AppRecallKotlinx_coroutines_coreJob, AppRecallKtor_utilsAttributes, AppRecallKotlinCoroutineContextKey, AppRecallKotlinCoroutineContextElement, AppRecallKotlinContinuation, AppRecallKotlinContinuationInterceptor, AppRecallKotlinx_coroutines_coreRunnable, AppRecallKotlinx_serialization_coreSerializersModuleCollector, AppRecallKotlinKClass, AppRecallKtor_httpParameters, AppRecallKotlinMapEntry, AppRecallKtor_utilsStringValues, AppRecallKotlinx_coroutines_coreChildHandle, AppRecallKotlinx_coroutines_coreChildJob, AppRecallKotlinx_coroutines_coreDisposableHandle, AppRecallKotlinSequence, AppRecallKotlinx_coroutines_coreSelectClause0, AppRecallKotlinComparable, AppRecallKtor_client_coreHttpClientFeature, AppRecallKtor_httpHttpMessageBuilder, AppRecallKtor_ioByteReadChannel, AppRecallKtor_utilsTypeInfo, AppRecallKtor_client_coreHttpRequest, AppRecallKotlinSuspendFunction2, AppRecallKotlinKDeclarationContainer, AppRecallKotlinKAnnotatedElement, AppRecallKotlinKClassifier, AppRecallKotlinx_coroutines_coreParentJob, AppRecallKotlinx_coroutines_coreSelectInstance, AppRecallKotlinSuspendFunction0, AppRecallKtor_ioReadSession, AppRecallKotlinSuspendFunction1, AppRecallKotlinAppendable, AppRecallKotlinKType, AppRecallKtor_httpHttpMessage, AppRecallKotlinFunction, AppRecallKtor_ioObjectPool, AppRecallKtor_ioInput, AppRecallKtor_ioOutput;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

__attribute__((swift_name("KotlinBase")))
@interface AppRecallBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface AppRecallBase (AppRecallBaseCopying) <NSCopying>
@end;

__attribute__((swift_name("KotlinMutableSet")))
@interface AppRecallMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((swift_name("KotlinMutableDictionary")))
@interface AppRecallMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorAppRecallKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((swift_name("KotlinNumber")))
@interface AppRecallNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end;

__attribute__((swift_name("KotlinByte")))
@interface AppRecallByte : AppRecallNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((swift_name("KotlinUByte")))
@interface AppRecallUByte : AppRecallNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((swift_name("KotlinShort")))
@interface AppRecallShort : AppRecallNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((swift_name("KotlinUShort")))
@interface AppRecallUShort : AppRecallNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((swift_name("KotlinInt")))
@interface AppRecallInt : AppRecallNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((swift_name("KotlinUInt")))
@interface AppRecallUInt : AppRecallNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((swift_name("KotlinLong")))
@interface AppRecallLong : AppRecallNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((swift_name("KotlinULong")))
@interface AppRecallULong : AppRecallNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((swift_name("KotlinFloat")))
@interface AppRecallFloat : AppRecallNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((swift_name("KotlinDouble")))
@interface AppRecallDouble : AppRecallNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((swift_name("KotlinBoolean")))
@interface AppRecallBoolean : AppRecallNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AppRecallResponse")))
@interface AppRecallAppRecallResponse : AppRecallBase
- (instancetype)initWithUpdateAvailable:(BOOL)updateAvailable updateRequired:(BOOL)updateRequired recall:(BOOL)recall __attribute__((swift_name("init(updateAvailable:updateRequired:recall:)"))) __attribute__((objc_designated_initializer));
- (BOOL)component1 __attribute__((swift_name("component1()")));
- (BOOL)component2 __attribute__((swift_name("component2()")));
- (BOOL)component3 __attribute__((swift_name("component3()")));
- (AppRecallAppRecallResponse *)doCopyUpdateAvailable:(BOOL)updateAvailable updateRequired:(BOOL)updateRequired recall:(BOOL)recall __attribute__((swift_name("doCopy(updateAvailable:updateRequired:recall:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) BOOL recall __attribute__((swift_name("recall")));
@property (readonly) BOOL updateAvailable __attribute__((swift_name("updateAvailable")));
@property (readonly) BOOL updateRequired __attribute__((swift_name("updateRequired")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("AppRecallResponse.Companion")))
@interface AppRecallAppRecallResponseCompanion : AppRecallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<AppRecallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end;

__attribute__((swift_name("KotlinThrowable")))
@interface AppRecallKotlinThrowable : AppRecallBase
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (AppRecallKotlinArray<NSString *> *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) AppRecallKotlinThrowable * _Nullable cause __attribute__((swift_name("cause")));
@property (readonly) NSString * _Nullable message __attribute__((swift_name("message")));
- (NSError *)asError __attribute__((swift_name("asError()")));
@end;

__attribute__((swift_name("KotlinException")))
@interface AppRecallKotlinException : AppRecallKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RecallException")))
@interface AppRecallRecallException : AppRecallKotlinException
- (instancetype)initWithStatus:(int32_t)status error:(AppRecallKotlinException *)error __attribute__((swift_name("init(status:error:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithCause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) int32_t status __attribute__((swift_name("status")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SamdResponse")))
@interface AppRecallSamdResponse : AppRecallBase
- (instancetype)initWithSamdId:(NSString *)samdId status:(BOOL)status __attribute__((swift_name("init(samdId:status:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (BOOL)component2 __attribute__((swift_name("component2()")));
- (AppRecallSamdResponse *)doCopySamdId:(NSString *)samdId status:(BOOL)status __attribute__((swift_name("doCopy(samdId:status:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *samdId __attribute__((swift_name("samdId")));
@property (readonly) BOOL status __attribute__((swift_name("status")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RecallApiClient")))
@interface AppRecallRecallApiClient : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithHttpClientEngine:(id<AppRecallKtor_client_coreHttpClientEngine>)httpClientEngine __attribute__((swift_name("init(httpClientEngine:)"))) __attribute__((objc_designated_initializer));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)checkAppRecallBaseURL:(NSString *)baseURL appId:(NSString *)appId appVersion:(NSString *)appVersion country:(NSString *)country completionHandler:(void (^)(AppRecallAppRecallResponse * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("checkAppRecall(baseURL:appId:appVersion:country:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)checkSaMDRecallBaseURL:(NSString *)baseURL country:(NSString *)country samdIds:(NSArray<NSString *> *)samdIds completionHandler:(void (^)(NSArray<AppRecallSamdResponse *> * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("checkSaMDRecall(baseURL:country:samdIds:completionHandler:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RecallApiClient.Companion")))
@interface AppRecallRecallApiClientCompanion : AppRecallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (readonly) NSString *APP_RECALL_END_POINT __attribute__((swift_name("APP_RECALL_END_POINT")));
@property (readonly) NSString *SAMD_RECALL_END_POINT __attribute__((swift_name("SAMD_RECALL_END_POINT")));
@property (readonly) int64_t TIME_OUT __attribute__((swift_name("TIME_OUT")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PlatformKt")))
@interface AppRecallPlatformKt : AppRecallBase
+ (NSString *)getDevice __attribute__((swift_name("getDevice()")));
+ (NSString *)getOS __attribute__((swift_name("getOS()")));
+ (NSString *)getOSVersion __attribute__((swift_name("getOSVersion()")));
+ (void)doInitLogger __attribute__((swift_name("doInitLogger()")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreSerializationStrategy")))
@protocol AppRecallKotlinx_serialization_coreSerializationStrategy
@required
- (void)serializeEncoder:(id<AppRecallKotlinx_serialization_coreEncoder>)encoder value:(id _Nullable)value __attribute__((swift_name("serialize(encoder:value:)")));
@property (readonly) id<AppRecallKotlinx_serialization_coreSerialDescriptor> descriptor __attribute__((swift_name("descriptor")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreDeserializationStrategy")))
@protocol AppRecallKotlinx_serialization_coreDeserializationStrategy
@required
- (id _Nullable)deserializeDecoder:(id<AppRecallKotlinx_serialization_coreDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
@property (readonly) id<AppRecallKotlinx_serialization_coreSerialDescriptor> descriptor __attribute__((swift_name("descriptor")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreKSerializer")))
@protocol AppRecallKotlinx_serialization_coreKSerializer <AppRecallKotlinx_serialization_coreSerializationStrategy, AppRecallKotlinx_serialization_coreDeserializationStrategy>
@required
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface AppRecallKotlinArray<T> : AppRecallBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(AppRecallInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<AppRecallKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineScope")))
@protocol AppRecallKotlinx_coroutines_coreCoroutineScope
@required
@property (readonly) id<AppRecallKotlinCoroutineContext> coroutineContext __attribute__((swift_name("coroutineContext")));
@end;

__attribute__((swift_name("Ktor_ioCloseable")))
@protocol AppRecallKtor_ioCloseable
@required
- (void)close __attribute__((swift_name("close()")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpClientEngine")))
@protocol AppRecallKtor_client_coreHttpClientEngine <AppRecallKotlinx_coroutines_coreCoroutineScope, AppRecallKtor_ioCloseable>
@required

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)executeData:(AppRecallKtor_client_coreHttpRequestData *)data completionHandler:(void (^)(AppRecallKtor_client_coreHttpResponseData * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("execute(data:completionHandler:)")));
- (void)installClient:(AppRecallKtor_client_coreHttpClient *)client __attribute__((swift_name("install(client:)")));
@property (readonly) AppRecallKtor_client_coreHttpClientEngineConfig *config __attribute__((swift_name("config")));
@property (readonly) AppRecallKotlinx_coroutines_coreCoroutineDispatcher *dispatcher __attribute__((swift_name("dispatcher")));
@property (readonly) NSSet<id<AppRecallKtor_client_coreHttpClientEngineCapability>> *supportedCapabilities __attribute__((swift_name("supportedCapabilities")));
@end;

__attribute__((swift_name("KotlinRuntimeException")))
@interface AppRecallKotlinRuntimeException : AppRecallKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinIllegalStateException")))
@interface AppRecallKotlinIllegalStateException : AppRecallKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinCancellationException")))
@interface AppRecallKotlinCancellationException : AppRecallKotlinIllegalStateException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreEncoder")))
@protocol AppRecallKotlinx_serialization_coreEncoder
@required
- (id<AppRecallKotlinx_serialization_coreCompositeEncoder>)beginCollectionDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor collectionSize:(int32_t)collectionSize __attribute__((swift_name("beginCollection(descriptor:collectionSize:)")));
- (id<AppRecallKotlinx_serialization_coreCompositeEncoder>)beginStructureDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("beginStructure(descriptor:)")));
- (void)encodeBooleanValue:(BOOL)value __attribute__((swift_name("encodeBoolean(value:)")));
- (void)encodeByteValue:(int8_t)value __attribute__((swift_name("encodeByte(value:)")));
- (void)encodeCharValue:(unichar)value __attribute__((swift_name("encodeChar(value:)")));
- (void)encodeDoubleValue:(double)value __attribute__((swift_name("encodeDouble(value:)")));
- (void)encodeEnumEnumDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)enumDescriptor index:(int32_t)index __attribute__((swift_name("encodeEnum(enumDescriptor:index:)")));
- (void)encodeFloatValue:(float)value __attribute__((swift_name("encodeFloat(value:)")));
- (id<AppRecallKotlinx_serialization_coreEncoder>)encodeInlineInlineDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)inlineDescriptor __attribute__((swift_name("encodeInline(inlineDescriptor:)")));
- (void)encodeIntValue:(int32_t)value __attribute__((swift_name("encodeInt(value:)")));
- (void)encodeLongValue:(int64_t)value __attribute__((swift_name("encodeLong(value:)")));
- (void)encodeNotNullMark __attribute__((swift_name("encodeNotNullMark()")));
- (void)encodeNull __attribute__((swift_name("encodeNull()")));
- (void)encodeNullableSerializableValueSerializer:(id<AppRecallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeNullableSerializableValue(serializer:value:)")));
- (void)encodeSerializableValueSerializer:(id<AppRecallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeSerializableValue(serializer:value:)")));
- (void)encodeShortValue:(int16_t)value __attribute__((swift_name("encodeShort(value:)")));
- (void)encodeStringValue:(NSString *)value __attribute__((swift_name("encodeString(value:)")));
@property (readonly) AppRecallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreSerialDescriptor")))
@protocol AppRecallKotlinx_serialization_coreSerialDescriptor
@required
- (NSArray<id<AppRecallKotlinAnnotation>> *)getElementAnnotationsIndex:(int32_t)index __attribute__((swift_name("getElementAnnotations(index:)")));
- (id<AppRecallKotlinx_serialization_coreSerialDescriptor>)getElementDescriptorIndex:(int32_t)index __attribute__((swift_name("getElementDescriptor(index:)")));
- (int32_t)getElementIndexName:(NSString *)name __attribute__((swift_name("getElementIndex(name:)")));
- (NSString *)getElementNameIndex:(int32_t)index __attribute__((swift_name("getElementName(index:)")));
- (BOOL)isElementOptionalIndex:(int32_t)index __attribute__((swift_name("isElementOptional(index:)")));
@property (readonly) NSArray<id<AppRecallKotlinAnnotation>> *annotations __attribute__((swift_name("annotations")));
@property (readonly) int32_t elementsCount __attribute__((swift_name("elementsCount")));
@property (readonly) BOOL isInline __attribute__((swift_name("isInline")));
@property (readonly) BOOL isNullable __attribute__((swift_name("isNullable")));
@property (readonly) AppRecallKotlinx_serialization_coreSerialKind *kind __attribute__((swift_name("kind")));
@property (readonly) NSString *serialName __attribute__((swift_name("serialName")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreDecoder")))
@protocol AppRecallKotlinx_serialization_coreDecoder
@required
- (id<AppRecallKotlinx_serialization_coreCompositeDecoder>)beginStructureDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("beginStructure(descriptor:)")));
- (BOOL)decodeBoolean __attribute__((swift_name("decodeBoolean()")));
- (int8_t)decodeByte __attribute__((swift_name("decodeByte()")));
- (unichar)decodeChar __attribute__((swift_name("decodeChar()")));
- (double)decodeDouble __attribute__((swift_name("decodeDouble()")));
- (int32_t)decodeEnumEnumDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)enumDescriptor __attribute__((swift_name("decodeEnum(enumDescriptor:)")));
- (float)decodeFloat __attribute__((swift_name("decodeFloat()")));
- (id<AppRecallKotlinx_serialization_coreDecoder>)decodeInlineInlineDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)inlineDescriptor __attribute__((swift_name("decodeInline(inlineDescriptor:)")));
- (int32_t)decodeInt __attribute__((swift_name("decodeInt()")));
- (int64_t)decodeLong __attribute__((swift_name("decodeLong()")));
- (BOOL)decodeNotNullMark __attribute__((swift_name("decodeNotNullMark()")));
- (AppRecallKotlinNothing * _Nullable)decodeNull __attribute__((swift_name("decodeNull()")));
- (id _Nullable)decodeNullableSerializableValueDeserializer:(id<AppRecallKotlinx_serialization_coreDeserializationStrategy>)deserializer __attribute__((swift_name("decodeNullableSerializableValue(deserializer:)")));
- (id _Nullable)decodeSerializableValueDeserializer:(id<AppRecallKotlinx_serialization_coreDeserializationStrategy>)deserializer __attribute__((swift_name("decodeSerializableValue(deserializer:)")));
- (int16_t)decodeShort __attribute__((swift_name("decodeShort()")));
- (NSString *)decodeString __attribute__((swift_name("decodeString()")));
@property (readonly) AppRecallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol AppRecallKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpRequestData")))
@interface AppRecallKtor_client_coreHttpRequestData : AppRecallBase
- (instancetype)initWithUrl:(AppRecallKtor_httpUrl *)url method:(AppRecallKtor_httpHttpMethod *)method headers:(id<AppRecallKtor_httpHeaders>)headers body:(AppRecallKtor_httpOutgoingContent *)body executionContext:(id<AppRecallKotlinx_coroutines_coreJob>)executionContext attributes:(id<AppRecallKtor_utilsAttributes>)attributes __attribute__((swift_name("init(url:method:headers:body:executionContext:attributes:)"))) __attribute__((objc_designated_initializer));
- (id _Nullable)getCapabilityOrNullKey:(id<AppRecallKtor_client_coreHttpClientEngineCapability>)key __attribute__((swift_name("getCapabilityOrNull(key:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) id<AppRecallKtor_utilsAttributes> attributes __attribute__((swift_name("attributes")));
@property (readonly) AppRecallKtor_httpOutgoingContent *body __attribute__((swift_name("body")));
@property (readonly) id<AppRecallKotlinx_coroutines_coreJob> executionContext __attribute__((swift_name("executionContext")));
@property (readonly) id<AppRecallKtor_httpHeaders> headers __attribute__((swift_name("headers")));
@property (readonly) AppRecallKtor_httpHttpMethod *method __attribute__((swift_name("method")));
@property (readonly) AppRecallKtor_httpUrl *url __attribute__((swift_name("url")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpResponseData")))
@interface AppRecallKtor_client_coreHttpResponseData : AppRecallBase
- (instancetype)initWithStatusCode:(AppRecallKtor_httpHttpStatusCode *)statusCode requestTime:(AppRecallKtor_utilsGMTDate *)requestTime headers:(id<AppRecallKtor_httpHeaders>)headers version:(AppRecallKtor_httpHttpProtocolVersion *)version body:(id)body callContext:(id<AppRecallKotlinCoroutineContext>)callContext __attribute__((swift_name("init(statusCode:requestTime:headers:version:body:callContext:)"))) __attribute__((objc_designated_initializer));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) id body __attribute__((swift_name("body")));
@property (readonly) id<AppRecallKotlinCoroutineContext> callContext __attribute__((swift_name("callContext")));
@property (readonly) id<AppRecallKtor_httpHeaders> headers __attribute__((swift_name("headers")));
@property (readonly) AppRecallKtor_utilsGMTDate *requestTime __attribute__((swift_name("requestTime")));
@property (readonly) AppRecallKtor_utilsGMTDate *responseTime __attribute__((swift_name("responseTime")));
@property (readonly) AppRecallKtor_httpHttpStatusCode *statusCode __attribute__((swift_name("statusCode")));
@property (readonly) AppRecallKtor_httpHttpProtocolVersion *version __attribute__((swift_name("version")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpClient")))
@interface AppRecallKtor_client_coreHttpClient : AppRecallBase <AppRecallKotlinx_coroutines_coreCoroutineScope, AppRecallKtor_ioCloseable>
- (instancetype)initWithEngine:(id<AppRecallKtor_client_coreHttpClientEngine>)engine userConfig:(AppRecallKtor_client_coreHttpClientConfig<AppRecallKtor_client_coreHttpClientEngineConfig *> *)userConfig __attribute__((swift_name("init(engine:userConfig:)"))) __attribute__((objc_designated_initializer));
- (void)close __attribute__((swift_name("close()")));
- (AppRecallKtor_client_coreHttpClient *)configBlock:(void (^)(AppRecallKtor_client_coreHttpClientConfig<id> *))block __attribute__((swift_name("config(block:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)executeBuilder:(AppRecallKtor_client_coreHttpRequestBuilder *)builder completionHandler:(void (^)(AppRecallKtor_client_coreHttpClientCall * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("execute(builder:completionHandler:)"))) __attribute__((unavailable("Unbound [HttpClientCall] is deprecated. Consider using [request<HttpResponse>(builder)] instead.")));
- (BOOL)isSupportedCapability:(id<AppRecallKtor_client_coreHttpClientEngineCapability>)capability __attribute__((swift_name("isSupported(capability:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) id<AppRecallKtor_utilsAttributes> attributes __attribute__((swift_name("attributes")));
@property (readonly) id<AppRecallKotlinCoroutineContext> coroutineContext __attribute__((swift_name("coroutineContext")));
@property (readonly) AppRecallKotlinx_coroutines_coreCoroutineDispatcher *dispatcher __attribute__((swift_name("dispatcher"))) __attribute__((unavailable("[dispatcher] is deprecated. Use coroutineContext instead.")));
@property (readonly) id<AppRecallKtor_client_coreHttpClientEngine> engine __attribute__((swift_name("engine")));
@property (readonly) AppRecallKtor_client_coreHttpClientEngineConfig *engineConfig __attribute__((swift_name("engineConfig")));
@property (readonly) AppRecallKtor_client_coreHttpReceivePipeline *receivePipeline __attribute__((swift_name("receivePipeline")));
@property (readonly) AppRecallKtor_client_coreHttpRequestPipeline *requestPipeline __attribute__((swift_name("requestPipeline")));
@property (readonly) AppRecallKtor_client_coreHttpResponsePipeline *responsePipeline __attribute__((swift_name("responsePipeline")));
@property (readonly) AppRecallKtor_client_coreHttpSendPipeline *sendPipeline __attribute__((swift_name("sendPipeline")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpClientEngineConfig")))
@interface AppRecallKtor_client_coreHttpClientEngineConfig : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property BOOL pipelining __attribute__((swift_name("pipelining")));
@property AppRecallKtor_client_coreProxyConfig * _Nullable proxy __attribute__((swift_name("proxy")));
@property (readonly) AppRecallKotlinNothing *response __attribute__((swift_name("response"))) __attribute__((unavailable("Response config is deprecated. See [HttpPlainText] feature for charset configuration")));
@property int32_t threadsCount __attribute__((swift_name("threadsCount")));
@end;

__attribute__((swift_name("KotlinCoroutineContext")))
@protocol AppRecallKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<AppRecallKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<AppRecallKotlinCoroutineContextElement> _Nullable)getKey:(id<AppRecallKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<AppRecallKotlinCoroutineContext>)minusKeyKey:(id<AppRecallKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<AppRecallKotlinCoroutineContext>)plusContext:(id<AppRecallKotlinCoroutineContext>)context __attribute__((swift_name("plus(context:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol AppRecallKotlinCoroutineContextElement <AppRecallKotlinCoroutineContext>
@required
@property (readonly) id<AppRecallKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end;

__attribute__((swift_name("KotlinAbstractCoroutineContextElement")))
@interface AppRecallKotlinAbstractCoroutineContextElement : AppRecallBase <AppRecallKotlinCoroutineContextElement>
- (instancetype)initWithKey:(id<AppRecallKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer));
@property (readonly) id<AppRecallKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end;

__attribute__((swift_name("KotlinContinuationInterceptor")))
@protocol AppRecallKotlinContinuationInterceptor <AppRecallKotlinCoroutineContextElement>
@required
- (id<AppRecallKotlinContinuation>)interceptContinuationContinuation:(id<AppRecallKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (void)releaseInterceptedContinuationContinuation:(id<AppRecallKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreCoroutineDispatcher")))
@interface AppRecallKotlinx_coroutines_coreCoroutineDispatcher : AppRecallKotlinAbstractCoroutineContextElement <AppRecallKotlinContinuationInterceptor>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithKey:(id<AppRecallKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (void)dispatchContext:(id<AppRecallKotlinCoroutineContext>)context block:(id<AppRecallKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatch(context:block:)")));
- (void)dispatchYieldContext:(id<AppRecallKotlinCoroutineContext>)context block:(id<AppRecallKotlinx_coroutines_coreRunnable>)block __attribute__((swift_name("dispatchYield(context:block:)")));
- (id<AppRecallKotlinContinuation>)interceptContinuationContinuation:(id<AppRecallKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (BOOL)isDispatchNeededContext:(id<AppRecallKotlinCoroutineContext>)context __attribute__((swift_name("isDispatchNeeded(context:)")));
- (AppRecallKotlinx_coroutines_coreCoroutineDispatcher *)plusOther:(AppRecallKotlinx_coroutines_coreCoroutineDispatcher *)other __attribute__((swift_name("plus(other:)"))) __attribute__((unavailable("Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")));
- (void)releaseInterceptedContinuationContinuation:(id<AppRecallKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpClientEngineCapability")))
@protocol AppRecallKtor_client_coreHttpClientEngineCapability
@required
@end;

__attribute__((swift_name("Kotlinx_serialization_coreCompositeEncoder")))
@protocol AppRecallKotlinx_serialization_coreCompositeEncoder
@required
- (void)encodeBooleanElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(BOOL)value __attribute__((swift_name("encodeBooleanElement(descriptor:index:value:)")));
- (void)encodeByteElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int8_t)value __attribute__((swift_name("encodeByteElement(descriptor:index:value:)")));
- (void)encodeCharElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(unichar)value __attribute__((swift_name("encodeCharElement(descriptor:index:value:)")));
- (void)encodeDoubleElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(double)value __attribute__((swift_name("encodeDoubleElement(descriptor:index:value:)")));
- (void)encodeFloatElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(float)value __attribute__((swift_name("encodeFloatElement(descriptor:index:value:)")));
- (id<AppRecallKotlinx_serialization_coreEncoder>)encodeInlineElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("encodeInlineElement(descriptor:index:)")));
- (void)encodeIntElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int32_t)value __attribute__((swift_name("encodeIntElement(descriptor:index:value:)")));
- (void)encodeLongElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int64_t)value __attribute__((swift_name("encodeLongElement(descriptor:index:value:)")));
- (void)encodeNullableSerializableElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index serializer:(id<AppRecallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeNullableSerializableElement(descriptor:index:serializer:value:)")));
- (void)encodeSerializableElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index serializer:(id<AppRecallKotlinx_serialization_coreSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeSerializableElement(descriptor:index:serializer:value:)")));
- (void)encodeShortElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(int16_t)value __attribute__((swift_name("encodeShortElement(descriptor:index:value:)")));
- (void)encodeStringElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index value:(NSString *)value __attribute__((swift_name("encodeStringElement(descriptor:index:value:)")));
- (void)endStructureDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("endStructure(descriptor:)")));
- (BOOL)shouldEncodeElementDefaultDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("shouldEncodeElementDefault(descriptor:index:)")));
@property (readonly) AppRecallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreSerializersModule")))
@interface AppRecallKotlinx_serialization_coreSerializersModule : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)dumpToCollector:(id<AppRecallKotlinx_serialization_coreSerializersModuleCollector>)collector __attribute__((swift_name("dumpTo(collector:)")));
- (id<AppRecallKotlinx_serialization_coreKSerializer> _Nullable)getContextualKClass:(id<AppRecallKotlinKClass>)kClass typeArgumentsSerializers:(NSArray<id<AppRecallKotlinx_serialization_coreKSerializer>> *)typeArgumentsSerializers __attribute__((swift_name("getContextual(kClass:typeArgumentsSerializers:)")));
- (id<AppRecallKotlinx_serialization_coreSerializationStrategy> _Nullable)getPolymorphicBaseClass:(id<AppRecallKotlinKClass>)baseClass value:(id)value __attribute__((swift_name("getPolymorphic(baseClass:value:)")));
- (id<AppRecallKotlinx_serialization_coreDeserializationStrategy> _Nullable)getPolymorphicBaseClass:(id<AppRecallKotlinKClass>)baseClass serializedClassName:(NSString * _Nullable)serializedClassName __attribute__((swift_name("getPolymorphic(baseClass:serializedClassName:)")));
@end;

__attribute__((swift_name("KotlinAnnotation")))
@protocol AppRecallKotlinAnnotation
@required
@end;

__attribute__((swift_name("Kotlinx_serialization_coreSerialKind")))
@interface AppRecallKotlinx_serialization_coreSerialKind : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreCompositeDecoder")))
@protocol AppRecallKotlinx_serialization_coreCompositeDecoder
@required
- (BOOL)decodeBooleanElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeBooleanElement(descriptor:index:)")));
- (int8_t)decodeByteElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeByteElement(descriptor:index:)")));
- (unichar)decodeCharElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeCharElement(descriptor:index:)")));
- (int32_t)decodeCollectionSizeDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("decodeCollectionSize(descriptor:)")));
- (double)decodeDoubleElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeDoubleElement(descriptor:index:)")));
- (int32_t)decodeElementIndexDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("decodeElementIndex(descriptor:)")));
- (float)decodeFloatElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeFloatElement(descriptor:index:)")));
- (id<AppRecallKotlinx_serialization_coreDecoder>)decodeInlineElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeInlineElement(descriptor:index:)")));
- (int32_t)decodeIntElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeIntElement(descriptor:index:)")));
- (int64_t)decodeLongElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeLongElement(descriptor:index:)")));
- (id _Nullable)decodeNullableSerializableElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index deserializer:(id<AppRecallKotlinx_serialization_coreDeserializationStrategy>)deserializer previousValue:(id _Nullable)previousValue __attribute__((swift_name("decodeNullableSerializableElement(descriptor:index:deserializer:previousValue:)")));
- (BOOL)decodeSequentially __attribute__((swift_name("decodeSequentially()")));
- (id _Nullable)decodeSerializableElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index deserializer:(id<AppRecallKotlinx_serialization_coreDeserializationStrategy>)deserializer previousValue:(id _Nullable)previousValue __attribute__((swift_name("decodeSerializableElement(descriptor:index:deserializer:previousValue:)")));
- (int16_t)decodeShortElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeShortElement(descriptor:index:)")));
- (NSString *)decodeStringElementDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor index:(int32_t)index __attribute__((swift_name("decodeStringElement(descriptor:index:)")));
- (void)endStructureDescriptor:(id<AppRecallKotlinx_serialization_coreSerialDescriptor>)descriptor __attribute__((swift_name("endStructure(descriptor:)")));
@property (readonly) AppRecallKotlinx_serialization_coreSerializersModule *serializersModule __attribute__((swift_name("serializersModule")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinNothing")))
@interface AppRecallKotlinNothing : AppRecallBase
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpUrl")))
@interface AppRecallKtor_httpUrl : AppRecallBase
- (instancetype)initWithProtocol:(AppRecallKtor_httpURLProtocol *)protocol host:(NSString *)host specifiedPort:(int32_t)specifiedPort encodedPath:(NSString *)encodedPath parameters:(id<AppRecallKtor_httpParameters>)parameters fragment:(NSString *)fragment user:(NSString * _Nullable)user password:(NSString * _Nullable)password trailingQuery:(BOOL)trailingQuery __attribute__((swift_name("init(protocol:host:specifiedPort:encodedPath:parameters:fragment:user:password:trailingQuery:)"))) __attribute__((objc_designated_initializer));
- (AppRecallKtor_httpURLProtocol *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (int32_t)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (id<AppRecallKtor_httpParameters>)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NSString * _Nullable)component7 __attribute__((swift_name("component7()")));
- (NSString * _Nullable)component8 __attribute__((swift_name("component8()")));
- (BOOL)component9 __attribute__((swift_name("component9()")));
- (AppRecallKtor_httpUrl *)doCopyProtocol:(AppRecallKtor_httpURLProtocol *)protocol host:(NSString *)host specifiedPort:(int32_t)specifiedPort encodedPath:(NSString *)encodedPath parameters:(id<AppRecallKtor_httpParameters>)parameters fragment:(NSString *)fragment user:(NSString * _Nullable)user password:(NSString * _Nullable)password trailingQuery:(BOOL)trailingQuery __attribute__((swift_name("doCopy(protocol:host:specifiedPort:encodedPath:parameters:fragment:user:password:trailingQuery:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *encodedPath __attribute__((swift_name("encodedPath")));
@property (readonly) NSString *fragment __attribute__((swift_name("fragment")));
@property (readonly) NSString *host __attribute__((swift_name("host")));
@property (readonly) id<AppRecallKtor_httpParameters> parameters __attribute__((swift_name("parameters")));
@property (readonly) NSString * _Nullable password __attribute__((swift_name("password")));
@property (readonly) int32_t port __attribute__((swift_name("port")));
@property (readonly) AppRecallKtor_httpURLProtocol *protocol __attribute__((swift_name("protocol")));
@property (readonly) int32_t specifiedPort __attribute__((swift_name("specifiedPort")));
@property (readonly) BOOL trailingQuery __attribute__((swift_name("trailingQuery")));
@property (readonly) NSString * _Nullable user __attribute__((swift_name("user")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpHttpMethod")))
@interface AppRecallKtor_httpHttpMethod : AppRecallBase
- (instancetype)initWithValue:(NSString *)value __attribute__((swift_name("init(value:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (AppRecallKtor_httpHttpMethod *)doCopyValue:(NSString *)value __attribute__((swift_name("doCopy(value:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end;

__attribute__((swift_name("Ktor_utilsStringValues")))
@protocol AppRecallKtor_utilsStringValues
@required
- (BOOL)containsName:(NSString *)name __attribute__((swift_name("contains(name:)")));
- (BOOL)containsName:(NSString *)name value:(NSString *)value __attribute__((swift_name("contains(name:value:)")));
- (NSSet<id<AppRecallKotlinMapEntry>> *)entries __attribute__((swift_name("entries()")));
- (void)forEachBody:(void (^)(NSString *, NSArray<NSString *> *))body __attribute__((swift_name("forEach(body:)")));
- (NSString * _Nullable)getName:(NSString *)name __attribute__((swift_name("get(name:)")));
- (NSArray<NSString *> * _Nullable)getAllName:(NSString *)name __attribute__((swift_name("getAll(name:)")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
- (NSSet<NSString *> *)names __attribute__((swift_name("names()")));
@property (readonly) BOOL caseInsensitiveName __attribute__((swift_name("caseInsensitiveName")));
@end;

__attribute__((swift_name("Ktor_httpHeaders")))
@protocol AppRecallKtor_httpHeaders <AppRecallKtor_utilsStringValues>
@required
@end;

__attribute__((swift_name("Ktor_httpOutgoingContent")))
@interface AppRecallKtor_httpOutgoingContent : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id _Nullable)getPropertyKey:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("getProperty(key:)")));
- (void)setPropertyKey:(AppRecallKtor_utilsAttributeKey<id> *)key value:(id _Nullable)value __attribute__((swift_name("setProperty(key:value:)")));
@property (readonly) AppRecallLong * _Nullable contentLength __attribute__((swift_name("contentLength")));
@property (readonly) AppRecallKtor_httpContentType * _Nullable contentType __attribute__((swift_name("contentType")));
@property (readonly) id<AppRecallKtor_httpHeaders> headers __attribute__((swift_name("headers")));
@property (readonly) AppRecallKtor_httpHttpStatusCode * _Nullable status __attribute__((swift_name("status")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreJob")))
@protocol AppRecallKotlinx_coroutines_coreJob <AppRecallKotlinCoroutineContextElement>
@required
- (id<AppRecallKotlinx_coroutines_coreChildHandle>)attachChildChild:(id<AppRecallKotlinx_coroutines_coreChildJob>)child __attribute__((swift_name("attachChild(child:)")));
- (void)cancelCause:(AppRecallKotlinCancellationException * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (AppRecallKotlinCancellationException *)getCancellationException __attribute__((swift_name("getCancellationException()")));
- (id<AppRecallKotlinx_coroutines_coreDisposableHandle>)invokeOnCompletionOnCancelling:(BOOL)onCancelling invokeImmediately:(BOOL)invokeImmediately handler:(void (^)(AppRecallKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(onCancelling:invokeImmediately:handler:)")));
- (id<AppRecallKotlinx_coroutines_coreDisposableHandle>)invokeOnCompletionHandler:(void (^)(AppRecallKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(handler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)joinWithCompletionHandler:(void (^)(AppRecallKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("join(completionHandler:)")));
- (id<AppRecallKotlinx_coroutines_coreJob>)plusOther_:(id<AppRecallKotlinx_coroutines_coreJob>)other __attribute__((swift_name("plus(other_:)"))) __attribute__((unavailable("Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")));
- (BOOL)start __attribute__((swift_name("start()")));
@property (readonly) id<AppRecallKotlinSequence> children __attribute__((swift_name("children")));
@property (readonly) BOOL isActive __attribute__((swift_name("isActive")));
@property (readonly) BOOL isCancelled __attribute__((swift_name("isCancelled")));
@property (readonly) BOOL isCompleted __attribute__((swift_name("isCompleted")));
@property (readonly) id<AppRecallKotlinx_coroutines_coreSelectClause0> onJoin __attribute__((swift_name("onJoin")));
@end;

__attribute__((swift_name("Ktor_utilsAttributes")))
@protocol AppRecallKtor_utilsAttributes
@required
- (id)computeIfAbsentKey:(AppRecallKtor_utilsAttributeKey<id> *)key block:(id (^)(void))block __attribute__((swift_name("computeIfAbsent(key:block:)")));
- (BOOL)containsKey:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("contains(key:)")));
- (id)getKey_:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("get(key_:)")));
- (id _Nullable)getOrNullKey:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("getOrNull(key:)")));
- (void)putKey:(AppRecallKtor_utilsAttributeKey<id> *)key value:(id)value __attribute__((swift_name("put(key:value:)")));
- (void)removeKey:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("remove(key:)")));
- (id)takeKey:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("take(key:)")));
- (id _Nullable)takeOrNullKey:(AppRecallKtor_utilsAttributeKey<id> *)key __attribute__((swift_name("takeOrNull(key:)")));
@property (readonly) NSArray<AppRecallKtor_utilsAttributeKey<id> *> *allKeys __attribute__((swift_name("allKeys")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpHttpStatusCode")))
@interface AppRecallKtor_httpHttpStatusCode : AppRecallBase
- (instancetype)initWithValue:(int32_t)value description:(NSString *)description __attribute__((swift_name("init(value:description:)"))) __attribute__((objc_designated_initializer));
- (int32_t)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (AppRecallKtor_httpHttpStatusCode *)doCopyValue:(int32_t)value description:(NSString *)description __attribute__((swift_name("doCopy(value:description:)")));
- (AppRecallKtor_httpHttpStatusCode *)descriptionValue:(NSString *)value __attribute__((swift_name("description(value:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *description_ __attribute__((swift_name("description_")));
@property (readonly) int32_t value __attribute__((swift_name("value")));
@end;

__attribute__((swift_name("KotlinComparable")))
@protocol AppRecallKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_utilsGMTDate")))
@interface AppRecallKtor_utilsGMTDate : AppRecallBase <AppRecallKotlinComparable>
- (int32_t)compareToOther:(AppRecallKtor_utilsGMTDate *)other __attribute__((swift_name("compareTo(other:)")));
- (int32_t)component1 __attribute__((swift_name("component1()")));
- (int32_t)component2 __attribute__((swift_name("component2()")));
- (int32_t)component3 __attribute__((swift_name("component3()")));
- (AppRecallKtor_utilsWeekDay *)component4 __attribute__((swift_name("component4()")));
- (int32_t)component5 __attribute__((swift_name("component5()")));
- (int32_t)component6 __attribute__((swift_name("component6()")));
- (AppRecallKtor_utilsMonth *)component7 __attribute__((swift_name("component7()")));
- (int32_t)component8 __attribute__((swift_name("component8()")));
- (int64_t)component9 __attribute__((swift_name("component9()")));
- (AppRecallKtor_utilsGMTDate *)doCopySeconds:(int32_t)seconds minutes:(int32_t)minutes hours:(int32_t)hours dayOfWeek:(AppRecallKtor_utilsWeekDay *)dayOfWeek dayOfMonth:(int32_t)dayOfMonth dayOfYear:(int32_t)dayOfYear month:(AppRecallKtor_utilsMonth *)month year:(int32_t)year timestamp:(int64_t)timestamp __attribute__((swift_name("doCopy(seconds:minutes:hours:dayOfWeek:dayOfMonth:dayOfYear:month:year:timestamp:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t dayOfMonth __attribute__((swift_name("dayOfMonth")));
@property (readonly) AppRecallKtor_utilsWeekDay *dayOfWeek __attribute__((swift_name("dayOfWeek")));
@property (readonly) int32_t dayOfYear __attribute__((swift_name("dayOfYear")));
@property (readonly) int32_t hours __attribute__((swift_name("hours")));
@property (readonly) int32_t minutes __attribute__((swift_name("minutes")));
@property (readonly) AppRecallKtor_utilsMonth *month __attribute__((swift_name("month")));
@property (readonly) int32_t seconds __attribute__((swift_name("seconds")));
@property (readonly) int64_t timestamp __attribute__((swift_name("timestamp")));
@property (readonly) int32_t year __attribute__((swift_name("year")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpHttpProtocolVersion")))
@interface AppRecallKtor_httpHttpProtocolVersion : AppRecallBase
- (instancetype)initWithName:(NSString *)name major:(int32_t)major minor:(int32_t)minor __attribute__((swift_name("init(name:major:minor:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (int32_t)component2 __attribute__((swift_name("component2()")));
- (int32_t)component3 __attribute__((swift_name("component3()")));
- (AppRecallKtor_httpHttpProtocolVersion *)doCopyName:(NSString *)name major:(int32_t)major minor:(int32_t)minor __attribute__((swift_name("doCopy(name:major:minor:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t major __attribute__((swift_name("major")));
@property (readonly) int32_t minor __attribute__((swift_name("minor")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpClientConfig")))
@interface AppRecallKtor_client_coreHttpClientConfig<T> : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (AppRecallKtor_client_coreHttpClientConfig<T> *)clone __attribute__((swift_name("clone()")));
- (void)engineBlock:(void (^)(T))block __attribute__((swift_name("engine(block:)")));
- (void)installClient:(AppRecallKtor_client_coreHttpClient *)client __attribute__((swift_name("install(client:)")));
- (void)installFeature:(id<AppRecallKtor_client_coreHttpClientFeature>)feature configure:(void (^)(id))configure __attribute__((swift_name("install(feature:configure:)")));
- (void)installKey:(NSString *)key block:(void (^)(AppRecallKtor_client_coreHttpClient *))block __attribute__((swift_name("install(key:block:)")));
- (void)plusAssignOther:(AppRecallKtor_client_coreHttpClientConfig<T> *)other __attribute__((swift_name("plusAssign(other:)")));
@property BOOL developmentMode __attribute__((swift_name("developmentMode")));
@property BOOL expectSuccess __attribute__((swift_name("expectSuccess")));
@property BOOL followRedirects __attribute__((swift_name("followRedirects")));
@property BOOL useDefaultTransformers __attribute__((swift_name("useDefaultTransformers")));
@end;

__attribute__((swift_name("Ktor_httpHttpMessageBuilder")))
@protocol AppRecallKtor_httpHttpMessageBuilder
@required
@property (readonly) AppRecallKtor_httpHeadersBuilder *headers __attribute__((swift_name("headers")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpRequestBuilder")))
@interface AppRecallKtor_client_coreHttpRequestBuilder : AppRecallBase <AppRecallKtor_httpHttpMessageBuilder>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (AppRecallKtor_client_coreHttpRequestData *)build __attribute__((swift_name("build()")));
- (id _Nullable)getCapabilityOrNullKey:(id<AppRecallKtor_client_coreHttpClientEngineCapability>)key __attribute__((swift_name("getCapabilityOrNull(key:)")));
- (void)setAttributesBlock:(void (^)(id<AppRecallKtor_utilsAttributes>))block __attribute__((swift_name("setAttributes(block:)")));
- (void)setCapabilityKey:(id<AppRecallKtor_client_coreHttpClientEngineCapability>)key capability:(id)capability __attribute__((swift_name("setCapability(key:capability:)")));
- (AppRecallKtor_client_coreHttpRequestBuilder *)takeFromBuilder:(AppRecallKtor_client_coreHttpRequestBuilder *)builder __attribute__((swift_name("takeFrom(builder:)")));
- (AppRecallKtor_client_coreHttpRequestBuilder *)takeFromWithExecutionContextBuilder:(AppRecallKtor_client_coreHttpRequestBuilder *)builder __attribute__((swift_name("takeFromWithExecutionContext(builder:)")));
- (void)urlBlock:(void (^)(AppRecallKtor_httpURLBuilder *, AppRecallKtor_httpURLBuilder *))block __attribute__((swift_name("url(block:)")));
@property (readonly) id<AppRecallKtor_utilsAttributes> attributes __attribute__((swift_name("attributes")));
@property id body __attribute__((swift_name("body")));
@property (readonly) id<AppRecallKotlinx_coroutines_coreJob> executionContext __attribute__((swift_name("executionContext")));
@property (readonly) AppRecallKtor_httpHeadersBuilder *headers __attribute__((swift_name("headers")));
@property AppRecallKtor_httpHttpMethod *method __attribute__((swift_name("method")));
@property (readonly) AppRecallKtor_httpURLBuilder *url __attribute__((swift_name("url")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpClientCall")))
@interface AppRecallKtor_client_coreHttpClientCall : AppRecallBase <AppRecallKotlinx_coroutines_coreCoroutineScope>

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)getResponseContentWithCompletionHandler:(void (^)(id<AppRecallKtor_ioByteReadChannel> _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("getResponseContent(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)receiveInfo:(AppRecallKtor_client_coreTypeInfo *)info completionHandler:(void (^)(id _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("receive(info:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)receiveInfo:(id<AppRecallKtor_utilsTypeInfo>)info completionHandler_:(void (^)(id _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("receive(info:completionHandler_:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) BOOL allowDoubleReceive __attribute__((swift_name("allowDoubleReceive")));
@property (readonly) id<AppRecallKtor_utilsAttributes> attributes __attribute__((swift_name("attributes")));
@property (readonly) AppRecallKtor_client_coreHttpClient * _Nullable client __attribute__((swift_name("client")));
@property (readonly) id<AppRecallKotlinCoroutineContext> coroutineContext __attribute__((swift_name("coroutineContext")));
@property (readonly) id<AppRecallKtor_client_coreHttpRequest> request __attribute__((swift_name("request")));
@property (readonly) AppRecallKtor_client_coreHttpResponse *response __attribute__((swift_name("response")));
@end;

__attribute__((swift_name("Ktor_utilsPipeline")))
@interface AppRecallKtor_utilsPipeline<TSubject, TContext> : AppRecallBase
- (instancetype)initWithPhase:(AppRecallKtor_utilsPipelinePhase *)phase interceptors:(NSArray<id<AppRecallKotlinSuspendFunction2>> *)interceptors __attribute__((swift_name("init(phase:interceptors:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithPhases:(AppRecallKotlinArray<AppRecallKtor_utilsPipelinePhase *> *)phases __attribute__((swift_name("init(phases:)"))) __attribute__((objc_designated_initializer));
- (void)addPhasePhase:(AppRecallKtor_utilsPipelinePhase *)phase __attribute__((swift_name("addPhase(phase:)")));
- (void)afterIntercepted __attribute__((swift_name("afterIntercepted()")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)executeContext:(TContext)context subject:(TSubject)subject completionHandler:(void (^)(TSubject _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("execute(context:subject:completionHandler:)")));
- (void)insertPhaseAfterReference:(AppRecallKtor_utilsPipelinePhase *)reference phase:(AppRecallKtor_utilsPipelinePhase *)phase __attribute__((swift_name("insertPhaseAfter(reference:phase:)")));
- (void)insertPhaseBeforeReference:(AppRecallKtor_utilsPipelinePhase *)reference phase:(AppRecallKtor_utilsPipelinePhase *)phase __attribute__((swift_name("insertPhaseBefore(reference:phase:)")));
- (void)interceptPhase:(AppRecallKtor_utilsPipelinePhase *)phase block:(id<AppRecallKotlinSuspendFunction2>)block __attribute__((swift_name("intercept(phase:block:)")));
- (void)mergeFrom:(AppRecallKtor_utilsPipeline<TSubject, TContext> *)from __attribute__((swift_name("merge(from:)")));
@property (readonly) id<AppRecallKtor_utilsAttributes> attributes __attribute__((swift_name("attributes")));
@property (readonly) BOOL developmentMode __attribute__((swift_name("developmentMode")));
@property (readonly, getter=isEmpty_) BOOL isEmpty __attribute__((swift_name("isEmpty")));
@property (readonly) NSArray<AppRecallKtor_utilsPipelinePhase *> *items __attribute__((swift_name("items")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpReceivePipeline")))
@interface AppRecallKtor_client_coreHttpReceivePipeline : AppRecallKtor_utilsPipeline<AppRecallKtor_client_coreHttpResponse *, AppRecallKtor_client_coreHttpClientCall *>
- (instancetype)initWithDevelopmentMode:(BOOL)developmentMode __attribute__((swift_name("init(developmentMode:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithPhase:(AppRecallKtor_utilsPipelinePhase *)phase interceptors:(NSArray<id<AppRecallKotlinSuspendFunction2>> *)interceptors __attribute__((swift_name("init(phase:interceptors:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithPhases:(AppRecallKotlinArray<AppRecallKtor_utilsPipelinePhase *> *)phases __attribute__((swift_name("init(phases:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) BOOL developmentMode __attribute__((swift_name("developmentMode")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpRequestPipeline")))
@interface AppRecallKtor_client_coreHttpRequestPipeline : AppRecallKtor_utilsPipeline<id, AppRecallKtor_client_coreHttpRequestBuilder *>
- (instancetype)initWithDevelopmentMode:(BOOL)developmentMode __attribute__((swift_name("init(developmentMode:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithPhase:(AppRecallKtor_utilsPipelinePhase *)phase interceptors:(NSArray<id<AppRecallKotlinSuspendFunction2>> *)interceptors __attribute__((swift_name("init(phase:interceptors:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithPhases:(AppRecallKotlinArray<AppRecallKtor_utilsPipelinePhase *> *)phases __attribute__((swift_name("init(phases:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) BOOL developmentMode __attribute__((swift_name("developmentMode")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpResponsePipeline")))
@interface AppRecallKtor_client_coreHttpResponsePipeline : AppRecallKtor_utilsPipeline<AppRecallKtor_client_coreHttpResponseContainer *, AppRecallKtor_client_coreHttpClientCall *>
- (instancetype)initWithDevelopmentMode:(BOOL)developmentMode __attribute__((swift_name("init(developmentMode:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithPhase:(AppRecallKtor_utilsPipelinePhase *)phase interceptors:(NSArray<id<AppRecallKotlinSuspendFunction2>> *)interceptors __attribute__((swift_name("init(phase:interceptors:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithPhases:(AppRecallKotlinArray<AppRecallKtor_utilsPipelinePhase *> *)phases __attribute__((swift_name("init(phases:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) BOOL developmentMode __attribute__((swift_name("developmentMode")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpSendPipeline")))
@interface AppRecallKtor_client_coreHttpSendPipeline : AppRecallKtor_utilsPipeline<id, AppRecallKtor_client_coreHttpRequestBuilder *>
- (instancetype)initWithDevelopmentMode:(BOOL)developmentMode __attribute__((swift_name("init(developmentMode:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithPhase:(AppRecallKtor_utilsPipelinePhase *)phase interceptors:(NSArray<id<AppRecallKotlinSuspendFunction2>> *)interceptors __attribute__((swift_name("init(phase:interceptors:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithPhases:(AppRecallKotlinArray<AppRecallKtor_utilsPipelinePhase *> *)phases __attribute__((swift_name("init(phases:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) BOOL developmentMode __attribute__((swift_name("developmentMode")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreProxyConfig")))
@interface AppRecallKtor_client_coreProxyConfig : AppRecallBase
- (instancetype)initWithUrl:(AppRecallKtor_httpUrl *)url __attribute__((swift_name("init(url:)"))) __attribute__((objc_designated_initializer));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) AppRecallKtor_httpUrl *url __attribute__((swift_name("url")));
@end;

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol AppRecallKotlinCoroutineContextKey
@required
@end;

__attribute__((swift_name("KotlinContinuation")))
@protocol AppRecallKotlinContinuation
@required
- (void)resumeWithResult:(id _Nullable)result __attribute__((swift_name("resumeWith(result:)")));
@property (readonly) id<AppRecallKotlinCoroutineContext> context __attribute__((swift_name("context")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreRunnable")))
@protocol AppRecallKotlinx_coroutines_coreRunnable
@required
- (void)run __attribute__((swift_name("run()")));
@end;

__attribute__((swift_name("Kotlinx_serialization_coreSerializersModuleCollector")))
@protocol AppRecallKotlinx_serialization_coreSerializersModuleCollector
@required
- (void)contextualKClass:(id<AppRecallKotlinKClass>)kClass provider:(id<AppRecallKotlinx_serialization_coreKSerializer> (^)(NSArray<id<AppRecallKotlinx_serialization_coreKSerializer>> *))provider __attribute__((swift_name("contextual(kClass:provider:)")));
- (void)contextualKClass:(id<AppRecallKotlinKClass>)kClass serializer:(id<AppRecallKotlinx_serialization_coreKSerializer>)serializer __attribute__((swift_name("contextual(kClass:serializer:)")));
- (void)polymorphicBaseClass:(id<AppRecallKotlinKClass>)baseClass actualClass:(id<AppRecallKotlinKClass>)actualClass actualSerializer:(id<AppRecallKotlinx_serialization_coreKSerializer>)actualSerializer __attribute__((swift_name("polymorphic(baseClass:actualClass:actualSerializer:)")));
- (void)polymorphicDefaultBaseClass:(id<AppRecallKotlinKClass>)baseClass defaultSerializerProvider:(id<AppRecallKotlinx_serialization_coreDeserializationStrategy> _Nullable (^)(NSString * _Nullable))defaultSerializerProvider __attribute__((swift_name("polymorphicDefault(baseClass:defaultSerializerProvider:)")));
@end;

__attribute__((swift_name("KotlinKDeclarationContainer")))
@protocol AppRecallKotlinKDeclarationContainer
@required
@end;

__attribute__((swift_name("KotlinKAnnotatedElement")))
@protocol AppRecallKotlinKAnnotatedElement
@required
@end;

__attribute__((swift_name("KotlinKClassifier")))
@protocol AppRecallKotlinKClassifier
@required
@end;

__attribute__((swift_name("KotlinKClass")))
@protocol AppRecallKotlinKClass <AppRecallKotlinKDeclarationContainer, AppRecallKotlinKAnnotatedElement, AppRecallKotlinKClassifier>
@required
- (BOOL)isInstanceValue:(id _Nullable)value __attribute__((swift_name("isInstance(value:)")));
@property (readonly) NSString * _Nullable qualifiedName __attribute__((swift_name("qualifiedName")));
@property (readonly) NSString * _Nullable simpleName __attribute__((swift_name("simpleName")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpURLProtocol")))
@interface AppRecallKtor_httpURLProtocol : AppRecallBase
- (instancetype)initWithName:(NSString *)name defaultPort:(int32_t)defaultPort __attribute__((swift_name("init(name:defaultPort:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (int32_t)component2 __attribute__((swift_name("component2()")));
- (AppRecallKtor_httpURLProtocol *)doCopyName:(NSString *)name defaultPort:(int32_t)defaultPort __attribute__((swift_name("doCopy(name:defaultPort:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t defaultPort __attribute__((swift_name("defaultPort")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((swift_name("Ktor_httpParameters")))
@protocol AppRecallKtor_httpParameters <AppRecallKtor_utilsStringValues>
@required
@property (readonly) AppRecallKtor_httpUrlEncodingOption *urlEncodingOption __attribute__((swift_name("urlEncodingOption")));
@end;

__attribute__((swift_name("KotlinMapEntry")))
@protocol AppRecallKotlinMapEntry
@required
@property (readonly) id _Nullable key __attribute__((swift_name("key")));
@property (readonly) id _Nullable value __attribute__((swift_name("value")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_utilsAttributeKey")))
@interface AppRecallKtor_utilsAttributeKey<T> : AppRecallBase
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((swift_name("Ktor_httpHeaderValueWithParameters")))
@interface AppRecallKtor_httpHeaderValueWithParameters : AppRecallBase
- (instancetype)initWithContent:(NSString *)content parameters:(NSArray<AppRecallKtor_httpHeaderValueParam *> *)parameters __attribute__((swift_name("init(content:parameters:)"))) __attribute__((objc_designated_initializer));
- (NSString * _Nullable)parameterName:(NSString *)name __attribute__((swift_name("parameter(name:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *content __attribute__((swift_name("content")));
@property (readonly) NSArray<AppRecallKtor_httpHeaderValueParam *> *parameters __attribute__((swift_name("parameters")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpContentType")))
@interface AppRecallKtor_httpContentType : AppRecallKtor_httpHeaderValueWithParameters
- (instancetype)initWithContentType:(NSString *)contentType contentSubtype:(NSString *)contentSubtype parameters:(NSArray<AppRecallKtor_httpHeaderValueParam *> *)parameters __attribute__((swift_name("init(contentType:contentSubtype:parameters:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithContent:(NSString *)content parameters:(NSArray<AppRecallKtor_httpHeaderValueParam *> *)parameters __attribute__((swift_name("init(content:parameters:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (BOOL)matchPattern:(AppRecallKtor_httpContentType *)pattern __attribute__((swift_name("match(pattern:)")));
- (BOOL)matchPattern_:(NSString *)pattern __attribute__((swift_name("match(pattern_:)")));
- (AppRecallKtor_httpContentType *)withParameterName:(NSString *)name value:(NSString *)value __attribute__((swift_name("withParameter(name:value:)")));
- (AppRecallKtor_httpContentType *)withoutParameters __attribute__((swift_name("withoutParameters()")));
@property (readonly) NSString *contentSubtype __attribute__((swift_name("contentSubtype")));
@property (readonly) NSString *contentType __attribute__((swift_name("contentType")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreDisposableHandle")))
@protocol AppRecallKotlinx_coroutines_coreDisposableHandle
@required
- (void)dispose __attribute__((swift_name("dispose()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreChildHandle")))
@protocol AppRecallKotlinx_coroutines_coreChildHandle <AppRecallKotlinx_coroutines_coreDisposableHandle>
@required
- (BOOL)childCancelledCause:(AppRecallKotlinThrowable *)cause __attribute__((swift_name("childCancelled(cause:)")));
@property (readonly) id<AppRecallKotlinx_coroutines_coreJob> _Nullable parent __attribute__((swift_name("parent")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreChildJob")))
@protocol AppRecallKotlinx_coroutines_coreChildJob <AppRecallKotlinx_coroutines_coreJob>
@required
- (void)parentCancelledParentJob:(id<AppRecallKotlinx_coroutines_coreParentJob>)parentJob __attribute__((swift_name("parentCancelled(parentJob:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface AppRecallKotlinUnit : AppRecallBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
- (NSString *)description __attribute__((swift_name("description()")));
@end;

__attribute__((swift_name("KotlinSequence")))
@protocol AppRecallKotlinSequence
@required
- (id<AppRecallKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreSelectClause0")))
@protocol AppRecallKotlinx_coroutines_coreSelectClause0
@required
- (void)registerSelectClause0Select:(id<AppRecallKotlinx_coroutines_coreSelectInstance>)select block:(id<AppRecallKotlinSuspendFunction0>)block __attribute__((swift_name("registerSelectClause0(select:block:)")));
@end;

__attribute__((swift_name("KotlinEnum")))
@interface AppRecallKotlinEnum<E> : AppRecallBase <AppRecallKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_utilsWeekDay")))
@interface AppRecallKtor_utilsWeekDay : AppRecallKotlinEnum<AppRecallKtor_utilsWeekDay *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) AppRecallKtor_utilsWeekDay *monday __attribute__((swift_name("monday")));
@property (class, readonly) AppRecallKtor_utilsWeekDay *tuesday __attribute__((swift_name("tuesday")));
@property (class, readonly) AppRecallKtor_utilsWeekDay *wednesday __attribute__((swift_name("wednesday")));
@property (class, readonly) AppRecallKtor_utilsWeekDay *thursday __attribute__((swift_name("thursday")));
@property (class, readonly) AppRecallKtor_utilsWeekDay *friday __attribute__((swift_name("friday")));
@property (class, readonly) AppRecallKtor_utilsWeekDay *saturday __attribute__((swift_name("saturday")));
@property (class, readonly) AppRecallKtor_utilsWeekDay *sunday __attribute__((swift_name("sunday")));
+ (AppRecallKotlinArray<AppRecallKtor_utilsWeekDay *> *)values __attribute__((swift_name("values()")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_utilsMonth")))
@interface AppRecallKtor_utilsMonth : AppRecallKotlinEnum<AppRecallKtor_utilsMonth *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) AppRecallKtor_utilsMonth *january __attribute__((swift_name("january")));
@property (class, readonly) AppRecallKtor_utilsMonth *february __attribute__((swift_name("february")));
@property (class, readonly) AppRecallKtor_utilsMonth *march __attribute__((swift_name("march")));
@property (class, readonly) AppRecallKtor_utilsMonth *april __attribute__((swift_name("april")));
@property (class, readonly) AppRecallKtor_utilsMonth *may __attribute__((swift_name("may")));
@property (class, readonly) AppRecallKtor_utilsMonth *june __attribute__((swift_name("june")));
@property (class, readonly) AppRecallKtor_utilsMonth *july __attribute__((swift_name("july")));
@property (class, readonly) AppRecallKtor_utilsMonth *august __attribute__((swift_name("august")));
@property (class, readonly) AppRecallKtor_utilsMonth *september __attribute__((swift_name("september")));
@property (class, readonly) AppRecallKtor_utilsMonth *october __attribute__((swift_name("october")));
@property (class, readonly) AppRecallKtor_utilsMonth *november __attribute__((swift_name("november")));
@property (class, readonly) AppRecallKtor_utilsMonth *december __attribute__((swift_name("december")));
+ (AppRecallKotlinArray<AppRecallKtor_utilsMonth *> *)values __attribute__((swift_name("values()")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpClientFeature")))
@protocol AppRecallKtor_client_coreHttpClientFeature
@required
- (void)installFeature:(id)feature scope:(AppRecallKtor_client_coreHttpClient *)scope __attribute__((swift_name("install(feature:scope:)")));
- (id)prepareBlock:(void (^)(id))block __attribute__((swift_name("prepare(block:)")));
@property (readonly) AppRecallKtor_utilsAttributeKey<id> *key __attribute__((swift_name("key")));
@end;

__attribute__((swift_name("Ktor_utilsStringValuesBuilder")))
@interface AppRecallKtor_utilsStringValuesBuilder : AppRecallBase
- (instancetype)initWithCaseInsensitiveName:(BOOL)caseInsensitiveName size:(int32_t)size __attribute__((swift_name("init(caseInsensitiveName:size:)"))) __attribute__((objc_designated_initializer));
- (void)appendName:(NSString *)name value:(NSString *)value __attribute__((swift_name("append(name:value:)")));
- (void)appendAllStringValues:(id<AppRecallKtor_utilsStringValues>)stringValues __attribute__((swift_name("appendAll(stringValues:)")));
- (void)appendAllName:(NSString *)name values:(id)values __attribute__((swift_name("appendAll(name:values:)")));
- (void)appendMissingStringValues:(id<AppRecallKtor_utilsStringValues>)stringValues __attribute__((swift_name("appendMissing(stringValues:)")));
- (void)appendMissingName:(NSString *)name values:(id)values __attribute__((swift_name("appendMissing(name:values:)")));
- (id<AppRecallKtor_utilsStringValues>)build __attribute__((swift_name("build()")));
- (void)clear __attribute__((swift_name("clear()")));
- (BOOL)containsName:(NSString *)name __attribute__((swift_name("contains(name:)")));
- (BOOL)containsName:(NSString *)name value:(NSString *)value __attribute__((swift_name("contains(name:value:)")));
- (NSSet<id<AppRecallKotlinMapEntry>> *)entries __attribute__((swift_name("entries()")));
- (NSString * _Nullable)getName:(NSString *)name __attribute__((swift_name("get(name:)")));
- (NSArray<NSString *> * _Nullable)getAllName:(NSString *)name __attribute__((swift_name("getAll(name:)")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
- (NSSet<NSString *> *)names __attribute__((swift_name("names()")));
- (void)removeName:(NSString *)name __attribute__((swift_name("remove(name:)")));
- (BOOL)removeName:(NSString *)name value:(NSString *)value __attribute__((swift_name("remove(name:value:)")));
- (void)removeKeysWithNoEntries __attribute__((swift_name("removeKeysWithNoEntries()")));
- (void)setName:(NSString *)name value:(NSString *)value __attribute__((swift_name("set(name:value:)")));
- (void)validateNameName:(NSString *)name __attribute__((swift_name("validateName(name:)")));
- (void)validateValueValue:(NSString *)value __attribute__((swift_name("validateValue(value:)")));
@property BOOL built __attribute__((swift_name("built")));
@property (readonly) BOOL caseInsensitiveName __attribute__((swift_name("caseInsensitiveName")));
@property (readonly) AppRecallMutableDictionary<NSString *, NSMutableArray<NSString *> *> *values __attribute__((swift_name("values")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpHeadersBuilder")))
@interface AppRecallKtor_httpHeadersBuilder : AppRecallKtor_utilsStringValuesBuilder
- (instancetype)initWithSize:(int32_t)size __attribute__((swift_name("init(size:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCaseInsensitiveName:(BOOL)caseInsensitiveName size:(int32_t)size __attribute__((swift_name("init(caseInsensitiveName:size:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (id<AppRecallKtor_httpHeaders>)build __attribute__((swift_name("build()")));
- (void)validateNameName:(NSString *)name __attribute__((swift_name("validateName(name:)")));
- (void)validateValueValue:(NSString *)value __attribute__((swift_name("validateValue(value:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpURLBuilder")))
@interface AppRecallKtor_httpURLBuilder : AppRecallBase
- (instancetype)initWithProtocol:(AppRecallKtor_httpURLProtocol *)protocol host:(NSString *)host port:(int32_t)port user:(NSString * _Nullable)user password:(NSString * _Nullable)password encodedPath:(NSString *)encodedPath parameters:(AppRecallKtor_httpParametersBuilder *)parameters fragment:(NSString *)fragment trailingQuery:(BOOL)trailingQuery __attribute__((swift_name("init(protocol:host:port:user:password:encodedPath:parameters:fragment:trailingQuery:)"))) __attribute__((objc_designated_initializer));
- (AppRecallKtor_httpUrl *)build __attribute__((swift_name("build()")));
- (NSString *)buildString __attribute__((swift_name("buildString()")));
- (AppRecallKtor_httpURLBuilder *)pathComponents:(AppRecallKotlinArray<NSString *> *)components __attribute__((swift_name("path(components:)")));
- (AppRecallKtor_httpURLBuilder *)pathComponents_:(NSArray<NSString *> *)components __attribute__((swift_name("path(components_:)")));
@property NSString *encodedPath __attribute__((swift_name("encodedPath")));
@property NSString *fragment __attribute__((swift_name("fragment")));
@property NSString *host __attribute__((swift_name("host")));
@property (readonly) AppRecallKtor_httpParametersBuilder *parameters __attribute__((swift_name("parameters")));
@property NSString * _Nullable password __attribute__((swift_name("password")));
@property int32_t port __attribute__((swift_name("port")));
@property AppRecallKtor_httpURLProtocol *protocol __attribute__((swift_name("protocol")));
@property BOOL trailingQuery __attribute__((swift_name("trailingQuery")));
@property NSString * _Nullable user __attribute__((swift_name("user")));
@end;

__attribute__((swift_name("Ktor_ioByteReadChannel")))
@protocol AppRecallKtor_ioByteReadChannel
@required
- (BOOL)cancelCause_:(AppRecallKotlinThrowable * _Nullable)cause __attribute__((swift_name("cancel(cause_:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)discardMax:(int64_t)max completionHandler:(void (^)(AppRecallLong * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("discard(max:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)peekToDestination:(AppRecallKtor_ioMemory *)destination destinationOffset:(int64_t)destinationOffset offset:(int64_t)offset min:(int64_t)min max:(int64_t)max completionHandler:(void (^)(AppRecallLong * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("peekTo(destination:destinationOffset:offset:min:max:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readAvailableDst:(AppRecallKtor_ioIoBuffer *)dst completionHandler:(void (^)(AppRecallInt * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readAvailable(dst:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readAvailableDst:(AppRecallKotlinByteArray *)dst offset:(int32_t)offset length:(int32_t)length completionHandler:(void (^)(AppRecallInt * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readAvailable(dst:offset:length:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readAvailableDst:(void *)dst offset:(int32_t)offset length:(int32_t)length completionHandler_:(void (^)(AppRecallInt * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readAvailable(dst:offset:length:completionHandler_:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readAvailableDst:(void *)dst offset:(int64_t)offset length:(int64_t)length completionHandler__:(void (^)(AppRecallInt * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readAvailable(dst:offset:length:completionHandler__:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readBooleanWithCompletionHandler:(void (^)(AppRecallBoolean * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readBoolean(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readByteWithCompletionHandler:(void (^)(AppRecallByte * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readByte(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readDoubleWithCompletionHandler:(void (^)(AppRecallDouble * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readDouble(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readFloatWithCompletionHandler:(void (^)(AppRecallFloat * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readFloat(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readFullyDst:(AppRecallKtor_ioIoBuffer *)dst n:(int32_t)n completionHandler:(void (^)(AppRecallKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readFully(dst:n:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readFullyDst:(AppRecallKotlinByteArray *)dst offset:(int32_t)offset length:(int32_t)length completionHandler:(void (^)(AppRecallKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readFully(dst:offset:length:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readFullyDst:(void *)dst offset:(int32_t)offset length:(int32_t)length completionHandler_:(void (^)(AppRecallKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readFully(dst:offset:length:completionHandler_:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readFullyDst:(void *)dst offset:(int64_t)offset length:(int64_t)length completionHandler__:(void (^)(AppRecallKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readFully(dst:offset:length:completionHandler__:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readIntWithCompletionHandler:(void (^)(AppRecallInt * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readInt(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readLongWithCompletionHandler:(void (^)(AppRecallLong * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readLong(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readPacketSize:(int32_t)size headerSizeHint:(int32_t)headerSizeHint completionHandler:(void (^)(AppRecallKtor_ioByteReadPacket * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readPacket(size:headerSizeHint:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readRemainingLimit:(int64_t)limit headerSizeHint:(int32_t)headerSizeHint completionHandler:(void (^)(AppRecallKtor_ioByteReadPacket * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readRemaining(limit:headerSizeHint:completionHandler:)")));
- (void)readSessionConsumer:(void (^)(id<AppRecallKtor_ioReadSession>))consumer __attribute__((swift_name("readSession(consumer:)"))) __attribute__((deprecated("Use read { } instead.")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readShortWithCompletionHandler:(void (^)(AppRecallShort * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readShort(completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readSuspendableSessionConsumer:(id<AppRecallKotlinSuspendFunction1>)consumer completionHandler:(void (^)(AppRecallKotlinUnit * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readSuspendableSession(consumer:completionHandler:)"))) __attribute__((deprecated("Use read { } instead.")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readUTF8LineLimit:(int32_t)limit completionHandler:(void (^)(NSString * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readUTF8Line(limit:completionHandler:)")));

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)readUTF8LineToOut:(id<AppRecallKotlinAppendable>)out limit:(int32_t)limit completionHandler:(void (^)(AppRecallBoolean * _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("readUTF8LineTo(out:limit:completionHandler:)")));
@property (readonly) int32_t availableForRead __attribute__((swift_name("availableForRead")));
@property (readonly) AppRecallKotlinThrowable * _Nullable closedCause __attribute__((swift_name("closedCause")));
@property (readonly) BOOL isClosedForRead __attribute__((swift_name("isClosedForRead")));
@property (readonly) BOOL isClosedForWrite __attribute__((swift_name("isClosedForWrite")));
@property AppRecallKtor_ioByteOrder *readByteOrder __attribute__((swift_name("readByteOrder"))) __attribute__((unavailable("Setting byte order is no longer supported. Read/write in big endian and use reverseByteOrder() extensions.")));
@property (readonly) int64_t totalBytesRead __attribute__((swift_name("totalBytesRead")));
@end;

__attribute__((swift_name("Ktor_utilsTypeInfo")))
@protocol AppRecallKtor_utilsTypeInfo
@required
@property (readonly) id<AppRecallKotlinKType> _Nullable kotlinType __attribute__((swift_name("kotlinType")));
@property (readonly) id<AppRecallKotlinKType> reifiedType __attribute__((swift_name("reifiedType")));
@property (readonly) id<AppRecallKotlinKClass> type __attribute__((swift_name("type")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreTypeInfo")))
@interface AppRecallKtor_client_coreTypeInfo : AppRecallBase <AppRecallKtor_utilsTypeInfo>
- (instancetype)initWithType:(id<AppRecallKotlinKClass>)type reifiedType:(id<AppRecallKotlinKType>)reifiedType kotlinType:(id<AppRecallKotlinKType> _Nullable)kotlinType __attribute__((swift_name("init(type:reifiedType:kotlinType:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("This was moved to another package.")));
- (id<AppRecallKotlinKClass>)component1 __attribute__((swift_name("component1()")));
- (id<AppRecallKotlinKType>)component2 __attribute__((swift_name("component2()")));
- (id<AppRecallKotlinKType> _Nullable)component3 __attribute__((swift_name("component3()")));
- (AppRecallKtor_client_coreTypeInfo *)doCopyType:(id<AppRecallKotlinKClass>)type reifiedType:(id<AppRecallKotlinKType>)reifiedType kotlinType:(id<AppRecallKotlinKType> _Nullable)kotlinType __attribute__((swift_name("doCopy(type:reifiedType:kotlinType:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) id<AppRecallKotlinKType> _Nullable kotlinType __attribute__((swift_name("kotlinType")));
@property (readonly) id<AppRecallKotlinKType> reifiedType __attribute__((swift_name("reifiedType")));
@property (readonly) id<AppRecallKotlinKClass> type __attribute__((swift_name("type")));
@end;

__attribute__((swift_name("Ktor_httpHttpMessage")))
@protocol AppRecallKtor_httpHttpMessage
@required
@property (readonly) id<AppRecallKtor_httpHeaders> headers __attribute__((swift_name("headers")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpRequest")))
@protocol AppRecallKtor_client_coreHttpRequest <AppRecallKtor_httpHttpMessage, AppRecallKotlinx_coroutines_coreCoroutineScope>
@required
@property (readonly) id<AppRecallKtor_utilsAttributes> attributes __attribute__((swift_name("attributes")));
@property (readonly) AppRecallKtor_client_coreHttpClientCall *call __attribute__((swift_name("call")));
@property (readonly) AppRecallKtor_httpOutgoingContent *content __attribute__((swift_name("content")));
@property (readonly) AppRecallKtor_httpHttpMethod *method __attribute__((swift_name("method")));
@property (readonly) AppRecallKtor_httpUrl *url __attribute__((swift_name("url")));
@end;

__attribute__((swift_name("Ktor_client_coreHttpResponse")))
@interface AppRecallKtor_client_coreHttpResponse : AppRecallBase <AppRecallKtor_httpHttpMessage, AppRecallKotlinx_coroutines_coreCoroutineScope>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) AppRecallKtor_client_coreHttpClientCall *call __attribute__((swift_name("call")));
@property (readonly) id<AppRecallKtor_ioByteReadChannel> content __attribute__((swift_name("content")));
@property (readonly) AppRecallKtor_utilsGMTDate *requestTime __attribute__((swift_name("requestTime")));
@property (readonly) AppRecallKtor_utilsGMTDate *responseTime __attribute__((swift_name("responseTime")));
@property (readonly) AppRecallKtor_httpHttpStatusCode *status __attribute__((swift_name("status")));
@property (readonly) AppRecallKtor_httpHttpProtocolVersion *version __attribute__((swift_name("version")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_utilsPipelinePhase")))
@interface AppRecallKtor_utilsPipelinePhase : AppRecallBase
- (instancetype)initWithName:(NSString *)name __attribute__((swift_name("init(name:)"))) __attribute__((objc_designated_initializer));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((swift_name("KotlinFunction")))
@protocol AppRecallKotlinFunction
@required
@end;

__attribute__((swift_name("KotlinSuspendFunction2")))
@protocol AppRecallKotlinSuspendFunction2 <AppRecallKotlinFunction>
@required

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeP1:(id _Nullable)p1 p2:(id _Nullable)p2 completionHandler:(void (^)(id _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(p1:p2:completionHandler:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_client_coreHttpResponseContainer")))
@interface AppRecallKtor_client_coreHttpResponseContainer : AppRecallBase
- (instancetype)initWithExpectedType:(id<AppRecallKtor_utilsTypeInfo>)expectedType response:(id)response __attribute__((swift_name("init(expectedType:response:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithExpectedType:(AppRecallKtor_client_coreTypeInfo *)expectedType response_:(id)response __attribute__((swift_name("init(expectedType:response_:)"))) __attribute__((objc_designated_initializer));
- (AppRecallKtor_client_coreTypeInfo *)component1 __attribute__((swift_name("component1()")));
- (id)component2 __attribute__((swift_name("component2()")));
- (AppRecallKtor_client_coreHttpResponseContainer *)doCopyExpectedType:(AppRecallKtor_client_coreTypeInfo *)expectedType response:(id)response __attribute__((swift_name("doCopy(expectedType:response:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) AppRecallKtor_client_coreTypeInfo *expectedType __attribute__((swift_name("expectedType")));
@property (readonly) id response __attribute__((swift_name("response")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpUrlEncodingOption")))
@interface AppRecallKtor_httpUrlEncodingOption : AppRecallKotlinEnum<AppRecallKtor_httpUrlEncodingOption *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) AppRecallKtor_httpUrlEncodingOption *default_ __attribute__((swift_name("default_")));
@property (class, readonly) AppRecallKtor_httpUrlEncodingOption *keyOnly __attribute__((swift_name("keyOnly")));
@property (class, readonly) AppRecallKtor_httpUrlEncodingOption *valueOnly __attribute__((swift_name("valueOnly")));
@property (class, readonly) AppRecallKtor_httpUrlEncodingOption *noEncoding __attribute__((swift_name("noEncoding")));
+ (AppRecallKotlinArray<AppRecallKtor_httpUrlEncodingOption *> *)values __attribute__((swift_name("values()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpHeaderValueParam")))
@interface AppRecallKtor_httpHeaderValueParam : AppRecallBase
- (instancetype)initWithName:(NSString *)name value:(NSString *)value __attribute__((swift_name("init(name:value:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (AppRecallKtor_httpHeaderValueParam *)doCopyName:(NSString *)name value:(NSString *)value __attribute__((swift_name("doCopy(name:value:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) NSString *value __attribute__((swift_name("value")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreParentJob")))
@protocol AppRecallKotlinx_coroutines_coreParentJob <AppRecallKotlinx_coroutines_coreJob>
@required
- (AppRecallKotlinCancellationException *)getChildJobCancellationCause __attribute__((swift_name("getChildJobCancellationCause()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreSelectInstance")))
@protocol AppRecallKotlinx_coroutines_coreSelectInstance
@required
- (void)disposeOnSelectHandle:(id<AppRecallKotlinx_coroutines_coreDisposableHandle>)handle __attribute__((swift_name("disposeOnSelect(handle:)")));
- (id _Nullable)performAtomicTrySelectDesc:(AppRecallKotlinx_coroutines_coreAtomicDesc *)desc __attribute__((swift_name("performAtomicTrySelect(desc:)")));
- (void)resumeSelectWithExceptionException:(AppRecallKotlinThrowable *)exception __attribute__((swift_name("resumeSelectWithException(exception:)")));
- (BOOL)trySelect __attribute__((swift_name("trySelect()")));
- (id _Nullable)trySelectOtherOtherOp:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp * _Nullable)otherOp __attribute__((swift_name("trySelectOther(otherOp:)")));
@property (readonly) id<AppRecallKotlinContinuation> completion __attribute__((swift_name("completion")));
@property (readonly) BOOL isSelected __attribute__((swift_name("isSelected")));
@end;

__attribute__((swift_name("KotlinSuspendFunction0")))
@protocol AppRecallKotlinSuspendFunction0 <AppRecallKotlinFunction>
@required

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeWithCompletionHandler:(void (^)(id _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(completionHandler:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_httpParametersBuilder")))
@interface AppRecallKtor_httpParametersBuilder : AppRecallKtor_utilsStringValuesBuilder
- (instancetype)initWithSize:(int32_t)size urlEncodingOption:(AppRecallKtor_httpUrlEncodingOption *)urlEncodingOption __attribute__((swift_name("init(size:urlEncodingOption:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCaseInsensitiveName:(BOOL)caseInsensitiveName size:(int32_t)size __attribute__((swift_name("init(caseInsensitiveName:size:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (id<AppRecallKtor_httpParameters>)build __attribute__((swift_name("build()")));
@property AppRecallKtor_httpUrlEncodingOption *urlEncodingOption __attribute__((swift_name("urlEncodingOption")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_ioMemory")))
@interface AppRecallKtor_ioMemory : AppRecallBase
- (instancetype)initWithPointer:(void *)pointer size:(int64_t)size __attribute__((swift_name("init(pointer:size:)"))) __attribute__((objc_designated_initializer));
- (void)doCopyToDestination:(AppRecallKtor_ioMemory *)destination offset:(int32_t)offset length:(int32_t)length destinationOffset:(int32_t)destinationOffset __attribute__((swift_name("doCopyTo(destination:offset:length:destinationOffset:)")));
- (void)doCopyToDestination:(AppRecallKtor_ioMemory *)destination offset:(int64_t)offset length:(int64_t)length destinationOffset_:(int64_t)destinationOffset __attribute__((swift_name("doCopyTo(destination:offset:length:destinationOffset_:)")));
- (int8_t)loadAtIndex:(int32_t)index __attribute__((swift_name("loadAt(index:)")));
- (int8_t)loadAtIndex_:(int64_t)index __attribute__((swift_name("loadAt(index_:)")));
- (AppRecallKtor_ioMemory *)sliceOffset:(int32_t)offset length:(int32_t)length __attribute__((swift_name("slice(offset:length:)")));
- (AppRecallKtor_ioMemory *)sliceOffset:(int64_t)offset length_:(int64_t)length __attribute__((swift_name("slice(offset:length_:)")));
- (void)storeAtIndex:(int32_t)index value:(int8_t)value __attribute__((swift_name("storeAt(index:value:)")));
- (void)storeAtIndex:(int64_t)index value_:(int8_t)value __attribute__((swift_name("storeAt(index:value_:)")));
@property (readonly) void *pointer __attribute__((swift_name("pointer")));
@property (readonly) int64_t size __attribute__((swift_name("size")));
@property (readonly) int32_t size32 __attribute__((swift_name("size32")));
@end;

__attribute__((swift_name("Ktor_ioBuffer")))
@interface AppRecallKtor_ioBuffer : AppRecallBase
- (instancetype)initWithMemory:(AppRecallKtor_ioMemory *)memory __attribute__((swift_name("init(memory:)"))) __attribute__((objc_designated_initializer));
- (void)commitWrittenCount:(int32_t)count __attribute__((swift_name("commitWritten(count:)")));
- (int32_t)discardCount:(int32_t)count __attribute__((swift_name("discard(count:)"))) __attribute__((unavailable("Use discardExact instead.")));
- (int64_t)discardCount_:(int64_t)count __attribute__((swift_name("discard(count_:)"))) __attribute__((unavailable("Use discardExact instead.")));
- (void)discardExactCount:(int32_t)count __attribute__((swift_name("discardExact(count:)")));
- (AppRecallKtor_ioBuffer *)duplicate __attribute__((swift_name("duplicate()")));
- (void)duplicateToCopy:(AppRecallKtor_ioBuffer *)copy __attribute__((swift_name("duplicateTo(copy:)")));
- (int8_t)readByte __attribute__((swift_name("readByte()")));
- (void)reserveEndGapEndGap:(int32_t)endGap __attribute__((swift_name("reserveEndGap(endGap:)")));
- (void)reserveStartGapStartGap:(int32_t)startGap __attribute__((swift_name("reserveStartGap(startGap:)")));
- (void)reset __attribute__((swift_name("reset()")));
- (void)resetForRead __attribute__((swift_name("resetForRead()")));
- (void)resetForWrite __attribute__((swift_name("resetForWrite()")));
- (void)resetForWriteLimit:(int32_t)limit __attribute__((swift_name("resetForWrite(limit:)")));
- (void)rewindCount:(int32_t)count __attribute__((swift_name("rewind(count:)")));
- (NSString *)description __attribute__((swift_name("description()")));
- (int32_t)tryPeekByte __attribute__((swift_name("tryPeekByte()")));
- (int32_t)tryReadByte __attribute__((swift_name("tryReadByte()")));
- (void)writeByteValue:(int8_t)value __attribute__((swift_name("writeByte(value:)")));
@property id _Nullable attachment __attribute__((swift_name("attachment"))) __attribute__((deprecated("Will be removed. Inherit Buffer and add required fields instead.")));
@property (readonly) int32_t capacity __attribute__((swift_name("capacity")));
@property (readonly) int32_t endGap __attribute__((swift_name("endGap")));
@property (readonly) int32_t limit __attribute__((swift_name("limit")));
@property (readonly) AppRecallKtor_ioMemory *memory __attribute__((swift_name("memory")));
@property (readonly) int32_t readPosition __attribute__((swift_name("readPosition")));
@property (readonly) int32_t readRemaining __attribute__((swift_name("readRemaining")));
@property (readonly) int32_t startGap __attribute__((swift_name("startGap")));
@property (readonly) int32_t writePosition __attribute__((swift_name("writePosition")));
@property (readonly) int32_t writeRemaining __attribute__((swift_name("writeRemaining")));
@end;

__attribute__((swift_name("Ktor_ioChunkBuffer")))
@interface AppRecallKtor_ioChunkBuffer : AppRecallKtor_ioBuffer
- (instancetype)initWithMemory:(AppRecallKtor_ioMemory *)memory origin:(AppRecallKtor_ioChunkBuffer * _Nullable)origin parentPool:(id<AppRecallKtor_ioObjectPool> _Nullable)parentPool __attribute__((swift_name("init(memory:origin:parentPool:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMemory:(AppRecallKtor_ioMemory *)memory __attribute__((swift_name("init(memory:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (AppRecallKtor_ioChunkBuffer * _Nullable)cleanNext __attribute__((swift_name("cleanNext()")));
- (AppRecallKtor_ioChunkBuffer *)duplicate __attribute__((swift_name("duplicate()")));
- (void)releasePool:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("release(pool:)")));
- (void)reset __attribute__((swift_name("reset()")));
@property (getter=next_) AppRecallKtor_ioChunkBuffer * _Nullable next __attribute__((swift_name("next")));
@property (readonly) AppRecallKtor_ioChunkBuffer * _Nullable origin __attribute__((swift_name("origin")));
@property (readonly) int32_t referenceCount __attribute__((swift_name("referenceCount")));
@end;

__attribute__((swift_name("Ktor_ioInput")))
@protocol AppRecallKtor_ioInput <AppRecallKtor_ioCloseable>
@required
- (int64_t)discardN:(int64_t)n __attribute__((swift_name("discard(n:)")));
- (int64_t)peekToDestination:(AppRecallKtor_ioMemory *)destination destinationOffset:(int64_t)destinationOffset offset:(int64_t)offset min:(int64_t)min max:(int64_t)max __attribute__((swift_name("peekTo(destination:destinationOffset:offset:min:max:)")));
- (int8_t)readByte __attribute__((swift_name("readByte()")));
- (int32_t)tryPeek __attribute__((swift_name("tryPeek()")));
@property AppRecallKtor_ioByteOrder *byteOrder __attribute__((swift_name("byteOrder"))) __attribute__((unavailable("Not supported anymore. All operations are big endian by default. Use readXXXLittleEndian or readXXX then X.reverseByteOrder() instead.")));
@property (readonly) BOOL endOfInput __attribute__((swift_name("endOfInput")));
@end;

__attribute__((swift_name("KotlinAppendable")))
@protocol AppRecallKotlinAppendable
@required
- (id<AppRecallKotlinAppendable>)appendValue:(unichar)value __attribute__((swift_name("append(value:)")));
- (id<AppRecallKotlinAppendable>)appendValue_:(id _Nullable)value __attribute__((swift_name("append(value_:)")));
- (id<AppRecallKotlinAppendable>)appendValue:(id _Nullable)value startIndex:(int32_t)startIndex endIndex:(int32_t)endIndex __attribute__((swift_name("append(value:startIndex:endIndex:)")));
@end;

__attribute__((swift_name("Ktor_ioOutput")))
@protocol AppRecallKtor_ioOutput <AppRecallKotlinAppendable, AppRecallKtor_ioCloseable>
@required
- (id<AppRecallKotlinAppendable>)appendCsq:(AppRecallKotlinCharArray *)csq start:(int32_t)start end:(int32_t)end __attribute__((swift_name("append(csq:start:end:)")));
- (void)flush __attribute__((swift_name("flush()")));
- (void)writeByteV:(int8_t)v __attribute__((swift_name("writeByte(v:)")));
@property AppRecallKtor_ioByteOrder *byteOrder __attribute__((swift_name("byteOrder"))) __attribute__((deprecated("Write with writeXXXLittleEndian or do X.reverseByteOrder() and then writeXXX instead.")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_ioIoBuffer")))
@interface AppRecallKtor_ioIoBuffer : AppRecallKtor_ioChunkBuffer <AppRecallKtor_ioInput, AppRecallKtor_ioOutput>
- (instancetype)initWithMemory:(AppRecallKtor_ioMemory *)memory origin:(AppRecallKtor_ioChunkBuffer * _Nullable)origin __attribute__((swift_name("init(memory:origin:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Use Buffer instead.")));
- (instancetype)initWithContent:(void *)content contentCapacity:(int32_t)contentCapacity __attribute__((swift_name("init(content:contentCapacity:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Use Buffer instead.")));
- (instancetype)initWithMemory:(AppRecallKtor_ioMemory *)memory origin:(AppRecallKtor_ioChunkBuffer * _Nullable)origin parentPool:(id<AppRecallKtor_ioObjectPool> _Nullable)parentPool __attribute__((swift_name("init(memory:origin:parentPool:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (id<AppRecallKotlinAppendable>)appendValue:(unichar)c __attribute__((swift_name("append(value:)")));
- (id<AppRecallKotlinAppendable>)appendCsq:(AppRecallKotlinCharArray *)csq start:(int32_t)start end:(int32_t)end __attribute__((swift_name("append(csq:start:end:)")));
- (id<AppRecallKotlinAppendable>)appendValue_:(id _Nullable)csq __attribute__((swift_name("append(value_:)")));
- (id<AppRecallKotlinAppendable>)appendValue:(id _Nullable)csq startIndex:(int32_t)start endIndex:(int32_t)end __attribute__((swift_name("append(value:startIndex:endIndex:)")));
- (int32_t)appendCharsCsq:(AppRecallKotlinCharArray *)csq start:(int32_t)start end:(int32_t)end __attribute__((swift_name("appendChars(csq:start:end:)")));
- (int32_t)appendCharsCsq:(id)csq start:(int32_t)start end_:(int32_t)end __attribute__((swift_name("appendChars(csq:start:end_:)")));
- (void)close __attribute__((swift_name("close()")));
- (AppRecallKtor_ioIoBuffer *)duplicate __attribute__((swift_name("duplicate()")));
- (void)flush __attribute__((swift_name("flush()")));
- (AppRecallKtor_ioIoBuffer *)makeView __attribute__((swift_name("makeView()")));
- (int64_t)peekToDestination:(AppRecallKtor_ioMemory *)destination destinationOffset:(int64_t)destinationOffset offset:(int64_t)offset min:(int64_t)min max:(int64_t)max __attribute__((swift_name("peekTo(destination:destinationOffset:offset:min:max:)")));
- (int32_t)readDirectBlock:(AppRecallInt *(^)(id))block __attribute__((swift_name("readDirect(block:)")));
- (void)releasePool_:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("release(pool_:)")));
- (NSString *)description __attribute__((swift_name("description()")));
- (int32_t)tryPeek __attribute__((swift_name("tryPeek()")));
- (int32_t)writeDirectBlock:(AppRecallInt *(^)(id))block __attribute__((swift_name("writeDirect(block:)")));
@property AppRecallKtor_ioByteOrder *byteOrder __attribute__((swift_name("byteOrder"))) __attribute__((unavailable("Not supported anymore. All operations are big endian by default.")));
@property (readonly) BOOL endOfInput __attribute__((swift_name("endOfInput")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinByteArray")))
@interface AppRecallKotlinByteArray : AppRecallBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(AppRecallByte *(^)(AppRecallInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (int8_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (AppRecallKotlinByteIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(int8_t)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("Ktor_ioAbstractInput")))
@interface AppRecallKtor_ioAbstractInput : AppRecallBase <AppRecallKtor_ioInput>
- (instancetype)initWithHead:(AppRecallKtor_ioChunkBuffer *)head remaining:(int64_t)remaining pool:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("init(head:remaining:pool:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("AbstractInput is deprecated and will be merged with Input in 2.0.0")));
- (BOOL)canRead __attribute__((swift_name("canRead()")));
- (void)close __attribute__((swift_name("close()")));
- (void)closeSource __attribute__((swift_name("closeSource()")));
- (int32_t)discardN_:(int32_t)n __attribute__((swift_name("discard(n_:)")));
- (int64_t)discardN:(int64_t)n __attribute__((swift_name("discard(n:)")));
- (void)discardExactN:(int32_t)n __attribute__((swift_name("discardExact(n:)")));
- (AppRecallKtor_ioChunkBuffer * _Nullable)ensureNextHeadCurrent:(AppRecallKtor_ioChunkBuffer *)current __attribute__((swift_name("ensureNextHead(current:)")));
- (AppRecallKtor_ioChunkBuffer * _Nullable)fill __attribute__((swift_name("fill()")));
- (int32_t)fillDestination:(AppRecallKtor_ioMemory *)destination offset:(int32_t)offset length:(int32_t)length __attribute__((swift_name("fill(destination:offset:length:)")));
- (void)fixGapAfterReadCurrent:(AppRecallKtor_ioChunkBuffer *)current __attribute__((swift_name("fixGapAfterRead(current:)")));
- (BOOL)hasBytesN:(int32_t)n __attribute__((swift_name("hasBytes(n:)")));
- (void)markNoMoreChunksAvailable __attribute__((swift_name("markNoMoreChunksAvailable()")));
- (int64_t)peekToDestination:(AppRecallKtor_ioMemory *)destination destinationOffset:(int64_t)destinationOffset offset:(int64_t)offset min:(int64_t)min max:(int64_t)max __attribute__((swift_name("peekTo(destination:destinationOffset:offset:min:max:)")));
- (AppRecallKtor_ioChunkBuffer * _Nullable)prepareReadHeadMinSize:(int32_t)minSize __attribute__((swift_name("prepareReadHead(minSize:)")));
- (int8_t)readByte __attribute__((swift_name("readByte()")));
- (NSString *)readTextMin:(int32_t)min max:(int32_t)max __attribute__((swift_name("readText(min:max:)")));
- (int32_t)readTextOut:(id<AppRecallKotlinAppendable>)out min:(int32_t)min max:(int32_t)max __attribute__((swift_name("readText(out:min:max:)")));
- (NSString *)readTextExactExactCharacters:(int32_t)exactCharacters __attribute__((swift_name("readTextExact(exactCharacters:)")));
- (void)readTextExactOut:(id<AppRecallKotlinAppendable>)out exactCharacters:(int32_t)exactCharacters __attribute__((swift_name("readTextExact(out:exactCharacters:)")));
- (void)release_ __attribute__((swift_name("release()")));
- (int32_t)tryPeek __attribute__((swift_name("tryPeek()")));
- (void)updateHeadRemainingRemaining:(int32_t)remaining __attribute__((swift_name("updateHeadRemaining(remaining:)"))) __attribute__((unavailable("Not supported anymore.")));
@property AppRecallKtor_ioByteOrder *byteOrder __attribute__((swift_name("byteOrder"))) __attribute__((unavailable("Not supported anymore. All operations are big endian by default.")));
@property (readonly) BOOL endOfInput __attribute__((swift_name("endOfInput")));
@property (readonly) id<AppRecallKtor_ioObjectPool> pool __attribute__((swift_name("pool")));
@property (readonly) int64_t remaining __attribute__((swift_name("remaining")));
@end;

__attribute__((swift_name("Ktor_ioByteReadPacketBase")))
@interface AppRecallKtor_ioByteReadPacketBase : AppRecallKtor_ioAbstractInput
- (instancetype)initWithHead:(AppRecallKtor_ioChunkBuffer *)head remaining:(int64_t)remaining pool:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("init(head:remaining:pool:)"))) __attribute__((objc_designated_initializer)) __attribute__((deprecated("Will be removed in the future releases. Use Input or AbstractInput instead.")));
@end;

__attribute__((swift_name("Ktor_ioByteReadPacketPlatformBase")))
@interface AppRecallKtor_ioByteReadPacketPlatformBase : AppRecallKtor_ioByteReadPacketBase
- (instancetype)initWithHead:(AppRecallKtor_ioChunkBuffer *)head remaining:(int64_t)remaining pool:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("init(head:remaining:pool:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable("Will be removed in future releases.")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_ioByteReadPacket")))
@interface AppRecallKtor_ioByteReadPacket : AppRecallKtor_ioByteReadPacketPlatformBase <AppRecallKtor_ioInput>
- (instancetype)initWithHead:(AppRecallKtor_ioChunkBuffer *)head pool:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("init(head:pool:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithHead:(AppRecallKtor_ioChunkBuffer *)head remaining:(int64_t)remaining pool:(id<AppRecallKtor_ioObjectPool>)pool __attribute__((swift_name("init(head:remaining:pool:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (void)closeSource __attribute__((swift_name("closeSource()")));
- (AppRecallKtor_ioByteReadPacket *)doCopy __attribute__((swift_name("doCopy()")));
- (AppRecallKtor_ioChunkBuffer * _Nullable)fill __attribute__((swift_name("fill()")));
- (int32_t)fillDestination:(AppRecallKtor_ioMemory *)destination offset:(int32_t)offset length:(int32_t)length __attribute__((swift_name("fill(destination:offset:length:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@end;

__attribute__((swift_name("Ktor_ioReadSession")))
@protocol AppRecallKtor_ioReadSession
@required
- (int32_t)discardN_:(int32_t)n __attribute__((swift_name("discard(n_:)")));
- (AppRecallKtor_ioIoBuffer * _Nullable)requestAtLeast:(int32_t)atLeast __attribute__((swift_name("request(atLeast:)")));
@property (readonly) int32_t availableForRead __attribute__((swift_name("availableForRead")));
@end;

__attribute__((swift_name("KotlinSuspendFunction1")))
@protocol AppRecallKotlinSuspendFunction1 <AppRecallKotlinFunction>
@required

/**
 @note This method converts instances of CancellationException to errors.
 Other uncaught Kotlin exceptions are fatal.
*/
- (void)invokeP1:(id _Nullable)p1 completionHandler:(void (^)(id _Nullable, NSError * _Nullable))completionHandler __attribute__((swift_name("invoke(p1:completionHandler:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Ktor_ioByteOrder")))
@interface AppRecallKtor_ioByteOrder : AppRecallKotlinEnum<AppRecallKtor_ioByteOrder *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) AppRecallKtor_ioByteOrder *bigEndian __attribute__((swift_name("bigEndian")));
@property (class, readonly) AppRecallKtor_ioByteOrder *littleEndian __attribute__((swift_name("littleEndian")));
+ (AppRecallKotlinArray<AppRecallKtor_ioByteOrder *> *)values __attribute__((swift_name("values()")));
@end;

__attribute__((swift_name("KotlinKType")))
@protocol AppRecallKotlinKType
@required
@property (readonly) NSArray<AppRecallKotlinKTypeProjection *> *arguments __attribute__((swift_name("arguments")));
@property (readonly) id<AppRecallKotlinKClassifier> _Nullable classifier __attribute__((swift_name("classifier")));
@property (readonly) BOOL isMarkedNullable __attribute__((swift_name("isMarkedNullable")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreAtomicDesc")))
@interface AppRecallKotlinx_coroutines_coreAtomicDesc : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeOp:(AppRecallKotlinx_coroutines_coreAtomicOp<id> *)op failure:(id _Nullable)failure __attribute__((swift_name("complete(op:failure:)")));
- (id _Nullable)prepareOp:(AppRecallKotlinx_coroutines_coreAtomicOp<id> *)op __attribute__((swift_name("prepare(op:)")));
@property AppRecallKotlinx_coroutines_coreAtomicOp<id> *atomicOp __attribute__((swift_name("atomicOp")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreOpDescriptor")))
@interface AppRecallKotlinx_coroutines_coreOpDescriptor : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (BOOL)isEarlierThanThat:(AppRecallKotlinx_coroutines_coreOpDescriptor *)that __attribute__((swift_name("isEarlierThan(that:)")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) AppRecallKotlinx_coroutines_coreAtomicOp<id> * _Nullable atomicOp __attribute__((swift_name("atomicOp")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNode.PrepareOp")))
@interface AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp : AppRecallKotlinx_coroutines_coreOpDescriptor
- (instancetype)initWithAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next desc:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc *)desc __attribute__((swift_name("init(affected:next:desc:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (void)finishPrepare __attribute__((swift_name("finishPrepare()")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *affected __attribute__((swift_name("affected")));
@property (readonly) AppRecallKotlinx_coroutines_coreAtomicOp<id> *atomicOp __attribute__((swift_name("atomicOp")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc *desc __attribute__((swift_name("desc")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *next __attribute__((swift_name("next")));
@end;

__attribute__((swift_name("Ktor_ioObjectPool")))
@protocol AppRecallKtor_ioObjectPool <AppRecallKtor_ioCloseable>
@required
- (id)borrow __attribute__((swift_name("borrow()")));
- (void)dispose __attribute__((swift_name("dispose()")));
- (void)recycleInstance:(id)instance __attribute__((swift_name("recycle(instance:)")));
@property (readonly) int32_t capacity __attribute__((swift_name("capacity")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinCharArray")))
@interface AppRecallKotlinCharArray : AppRecallBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(id (^)(AppRecallInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (unichar)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (AppRecallKotlinCharIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(unichar)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("KotlinByteIterator")))
@interface AppRecallKotlinByteIterator : AppRecallBase <AppRecallKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (AppRecallByte *)next __attribute__((swift_name("next()")));
- (int8_t)nextByte __attribute__((swift_name("nextByte()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinKTypeProjection")))
@interface AppRecallKotlinKTypeProjection : AppRecallBase
- (instancetype)initWithVariance:(AppRecallKotlinKVariance * _Nullable)variance type:(id<AppRecallKotlinKType> _Nullable)type __attribute__((swift_name("init(variance:type:)"))) __attribute__((objc_designated_initializer));
- (AppRecallKotlinKVariance * _Nullable)component1 __attribute__((swift_name("component1()")));
- (id<AppRecallKotlinKType> _Nullable)component2 __attribute__((swift_name("component2()")));
- (AppRecallKotlinKTypeProjection *)doCopyVariance:(AppRecallKotlinKVariance * _Nullable)variance type:(id<AppRecallKotlinKType> _Nullable)type __attribute__((swift_name("doCopy(variance:type:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) id<AppRecallKotlinKType> _Nullable type __attribute__((swift_name("type")));
@property (readonly) AppRecallKotlinKVariance * _Nullable variance __attribute__((swift_name("variance")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreAtomicOp")))
@interface AppRecallKotlinx_coroutines_coreAtomicOp<__contravariant T> : AppRecallKotlinx_coroutines_coreOpDescriptor
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeAffected:(T _Nullable)affected failure:(id _Nullable)failure __attribute__((swift_name("complete(affected:failure:)")));
- (id _Nullable)decideDecision:(id _Nullable)decision __attribute__((swift_name("decide(decision:)")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
- (id _Nullable)prepareAffected:(T _Nullable)affected __attribute__((swift_name("prepare(affected:)")));
@property (readonly) AppRecallKotlinx_coroutines_coreAtomicOp<id> *atomicOp __attribute__((swift_name("atomicOp")));
@property (readonly) id _Nullable consensus __attribute__((swift_name("consensus")));
@property (readonly) BOOL isDecided __attribute__((swift_name("isDecided")));
@property (readonly) int64_t opSequence __attribute__((swift_name("opSequence")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNode")))
@interface AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode : AppRecallBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)addLastNode:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)node __attribute__((swift_name("addLast(node:)")));
- (BOOL)addLastIfNode:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)node condition:(AppRecallBoolean *(^)(void))condition __attribute__((swift_name("addLastIf(node:condition:)")));
- (BOOL)addLastIfPrevNode:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)node predicate:(AppRecallBoolean *(^)(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *))predicate __attribute__((swift_name("addLastIfPrev(node:predicate:)")));
- (BOOL)addLastIfPrevAndIfNode:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)node predicate:(AppRecallBoolean *(^)(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *))predicate condition:(AppRecallBoolean *(^)(void))condition __attribute__((swift_name("addLastIfPrevAndIf(node:predicate:condition:)")));
- (BOOL)addOneIfEmptyNode:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)node __attribute__((swift_name("addOneIfEmpty(node:)")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc<AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *> *)describeAddLastNode:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)node __attribute__((swift_name("describeAddLast(node:)")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc<AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *> *)describeRemoveFirst __attribute__((swift_name("describeRemoveFirst()")));
- (void)helpRemove __attribute__((swift_name("helpRemove()")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)nextIfRemoved __attribute__((swift_name("nextIfRemoved()")));
- (BOOL)remove __attribute__((swift_name("remove()")));
- (id _Nullable)removeFirstIfIsInstanceOfOrPeekIfPredicate:(AppRecallBoolean *(^)(id _Nullable))predicate __attribute__((swift_name("removeFirstIfIsInstanceOfOrPeekIf(predicate:)")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)removeFirstOrNull __attribute__((swift_name("removeFirstOrNull()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) BOOL isRemoved __attribute__((swift_name("isRemoved")));
@property (readonly, getter=next_) id _Nullable next __attribute__((swift_name("next")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *nextNode __attribute__((swift_name("nextNode")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *prevNode __attribute__((swift_name("prevNode")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNode.AbstractAtomicDesc")))
@interface AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc : AppRecallKotlinx_coroutines_coreAtomicDesc
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeOp:(AppRecallKotlinx_coroutines_coreAtomicOp<id> *)op failure:(id _Nullable)failure __attribute__((swift_name("complete(op:failure:)")));
- (id _Nullable)failureAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)affected __attribute__((swift_name("failure(affected:)")));
- (void)finishOnSuccessAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("finishOnSuccess(affected:next:)")));
- (void)finishPreparePrepareOp:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("finishPrepare(prepareOp:)")));
- (id _Nullable)onPreparePrepareOp:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("onPrepare(prepareOp:)")));
- (void)onRemovedAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected __attribute__((swift_name("onRemoved(affected:)")));
- (id _Nullable)prepareOp:(AppRecallKotlinx_coroutines_coreAtomicOp<id> *)op __attribute__((swift_name("prepare(op:)")));
- (BOOL)retryAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(id)next __attribute__((swift_name("retry(affected:next:)")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)takeAffectedNodeOp:(AppRecallKotlinx_coroutines_coreOpDescriptor *)op __attribute__((swift_name("takeAffectedNode(op:)")));
- (id)updatedNextAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("updatedNext(affected:next:)")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable affectedNode __attribute__((swift_name("affectedNode")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable originalNext __attribute__((swift_name("originalNext")));
@end;

__attribute__((swift_name("KotlinCharIterator")))
@interface AppRecallKotlinCharIterator : AppRecallBase <AppRecallKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id)next __attribute__((swift_name("next()")));
- (unichar)nextChar __attribute__((swift_name("nextChar()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinKVariance")))
@interface AppRecallKotlinKVariance : AppRecallKotlinEnum<AppRecallKotlinKVariance *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) AppRecallKotlinKVariance *invariant __attribute__((swift_name("invariant")));
@property (class, readonly) AppRecallKotlinKVariance *in __attribute__((swift_name("in")));
@property (class, readonly) AppRecallKotlinKVariance *out __attribute__((swift_name("out")));
+ (AppRecallKotlinArray<AppRecallKotlinKVariance *> *)values __attribute__((swift_name("values()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc")))
@interface AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAddLastDesc<T> : AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc
- (instancetype)initWithQueue:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)queue node:(T)node __attribute__((swift_name("init(queue:node:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (void)finishOnSuccessAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("finishOnSuccess(affected:next:)")));
- (void)finishPreparePrepareOp:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("finishPrepare(prepareOp:)")));
- (BOOL)retryAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(id)next __attribute__((swift_name("retry(affected:next:)")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)takeAffectedNodeOp:(AppRecallKotlinx_coroutines_coreOpDescriptor *)op __attribute__((swift_name("takeAffectedNode(op:)")));
- (id)updatedNextAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("updatedNext(affected:next:)")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable affectedNode __attribute__((swift_name("affectedNode")));
@property (readonly) T node __attribute__((swift_name("node")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *originalNext __attribute__((swift_name("originalNext")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *queue __attribute__((swift_name("queue")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc")))
@interface AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeRemoveFirstDesc<T> : AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodeAbstractAtomicDesc
- (instancetype)initWithQueue:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)queue __attribute__((swift_name("init(queue:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (id _Nullable)failureAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)affected __attribute__((swift_name("failure(affected:)")));
- (void)finishOnSuccessAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("finishOnSuccess(affected:next:)")));
- (void)finishPreparePrepareOp:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNodePrepareOp *)prepareOp __attribute__((swift_name("finishPrepare(prepareOp:)")));
- (BOOL)retryAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(id)next __attribute__((swift_name("retry(affected:next:)")));
- (AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable)takeAffectedNodeOp:(AppRecallKotlinx_coroutines_coreOpDescriptor *)op __attribute__((swift_name("takeAffectedNode(op:)")));
- (id)updatedNextAffected:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)affected next:(AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *)next __attribute__((swift_name("updatedNext(affected:next:)")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable affectedNode __attribute__((swift_name("affectedNode")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode * _Nullable originalNext __attribute__((swift_name("originalNext")));
@property (readonly) AppRecallKotlinx_coroutines_coreLockFreeLinkedListNode *queue __attribute__((swift_name("queue")));
@property (readonly) T _Nullable result __attribute__((swift_name("result")));
@end;

#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
