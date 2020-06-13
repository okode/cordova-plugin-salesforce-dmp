#import <Cordova/CDV.h>
#import <iOSKruxLibUniversal/KruxTracker.h>

@interface CDVDMP : CDVPlugin

+ (KruxTracker *)getKruxTracker;

- (void) initialize:(CDVInvokedUrlCommand*)command;
- (void) sendRequests:(CDVInvokedUrlCommand*)command;
- (void) trackPage:(CDVInvokedUrlCommand*)command;
- (void) fireEvent:(CDVInvokedUrlCommand*)command;

- (void) addPolicyRegimeParameter: (NSDictionary *) attributes;
- (NSDictionary *) getIdParameters;
- (NSDictionary *) getConsentAttributes;

@end