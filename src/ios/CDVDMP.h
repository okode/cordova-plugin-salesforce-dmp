#import <Cordova/CDV.h>
#import <iOSKruxLibUniversal/KruxTracker.h>

@interface CDVDMP : CDVPlugin

+ (KruxTracker *)getKruxTracker;

- (void) initialize:(CDVInvokedUrlCommand*)command;
- (void) sendRequests:(CDVInvokedUrlCommand*)command;
- (void) getSegments:(CDVInvokedUrlCommand*)command;
- (void) trackPage:(CDVInvokedUrlCommand*)command;
- (void) fireEvent:(CDVInvokedUrlCommand*)command;

- (void) addPolicyRegimeParameter: (NSMutableDictionary *) attributes : (NSString *) pr;
- (NSMutableDictionary *) getUserParameters: (NSDictionary *) attributes;
- (NSMutableDictionary *) getPageParameters: (NSDictionary *) attributes;

@end